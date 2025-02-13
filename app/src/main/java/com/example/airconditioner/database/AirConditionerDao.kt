package com.example.airconditioner.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Dao
interface AirConditionerDao {
    @Insert
    suspend fun insert(airConditioner: AirConditioner)

    @Query("SELECT * FROM airConditioner")
    suspend fun getAllAirConditioner(): List<AirConditioner>

}


