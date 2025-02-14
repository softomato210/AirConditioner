package com.example.airconditioner

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.airconditioner.database.AirConditioner
import com.example.airconditioner.database.AppDatabase
import com.example.airconditioner.database.DatabaseProvider
import com.example.airconditioner.ftp.FtpDownloader
import com.example.airconditioner.input.Manager
import com.google.gson.Gson
import com.websarva.wings.android.airconditioner.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class AirConditionerViewModel(application: Application) : AndroidViewModel(application) {

    var isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
        private set
    private val ftpDownloader = FtpDownloader()

    val context = getApplication<Application>().applicationContext
    private val localDir = File(context.filesDir, "ftp_downloads").apply { mkdirs() }

    private val _managerList = MutableStateFlow<List<Manager>>(emptyList())
    val managerList: StateFlow<List<Manager>> = _managerList.asStateFlow()

    private val _filteredList = MutableStateFlow<List<Manager>>(emptyList())
    val filteredList: StateFlow<List<Manager>> = _filteredList.asStateFlow()

    init {
        ftpDownloader.downloadAllJsonFiles(
            host = BuildConfig.host,
            username = BuildConfig.username,
            password = BuildConfig.password,
            remotePath = BuildConfig.remotePath,
            localDirectoryPath = localDir.absolutePath
        ) { result ->
            println(result)

            viewModelScope.launch(Dispatchers.IO) {

                val managerList = loadManagerFromJsonFiles(context)

                try {
                    withContext(Dispatchers.Main) {
                        _managerList.value = managerList
                        isLoading.value = false
                    }
                }catch (e: Exception) {
                    Log.e("FTP_ERROR", "FTP 接続エラー: ${e.message}")
                }
            }
        }
    }

    private val db = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "app_database"
    ).fallbackToDestructiveMigration().build()

    private val _airConditioner = MutableStateFlow<List<AirConditioner>>(emptyList())
    val airConditioner: StateFlow<List<AirConditioner?>> = _airConditioner
    private val airConditionerDao = DatabaseProvider.getDatabase(application).airConditionerDao()

    fun getAllAirConditioner() {
        viewModelScope.launch {
            val airConditionerList = db.airConditionerDao().getAllAirConditioner()
            _airConditioner.value = airConditionerList
        }
    }
    /*
    fun onUpdateGarbage(updatedEquipment: Garbage, situation: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            garbageDao.update(updatedEquipment)
            //getEquipmentsExcludingSituation(situation)
        }
    }
     */

    fun addAirConditioner(airConditioner: AirConditioner) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("Debug", "Inserting AirConditioner: $airConditioner")
                airConditionerDao.insert(airConditioner)
                Log.d("Debug", "Insert successful.")
            } catch (e: Exception) {
                Log.e("Debug", "Insert failed: ${e.message}")
            }
        }
    }

    suspend fun loadManagerFromJsonFiles(context: Context): List<Manager> {
        return withContext(Dispatchers.IO) {
            val localDir = File(context.filesDir, "ftp_downloads").apply { mkdirs() }
            val targetFileName = BuildConfig.inputFile
            val jsonFiles = localDir.listFiles { it.name == targetFileName } ?: emptyArray()

            Log.d("FILE_CHECK", "Looking for file: $targetFileName, Found: ${jsonFiles.size}")

            val personalList = mutableListOf<Manager>()

            val gson = Gson()

            jsonFiles.forEach { file ->
                var jsonContent = file.readText()

                if (jsonContent.startsWith("\uFEFF")) { jsonContent = jsonContent.substring(1) }

                try {
                    val personalArray: Array<Manager> = gson.fromJson(
                        jsonContent, Array<Manager>::class.java)
                    personalList.addAll(personalArray)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("JSON_PARSE", "Failed to parse JSON: ${e.message}")
                }
            }
            personalList
        }
    }

    fun searchPersonalByCode(managerCdInput: String) {
        val filtered = managerList.value.filter { it.code.contains(managerCdInput) }
        _filteredList.value = filtered
    }

    fun getCdByName(managerInput: String): String {
        return managerList.value.find { it.name == managerInput }?.code ?: ""
    }
}
