package com.example.airconditioner.ftp

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.net.ftp.FTPClient
import java.io.FileOutputStream

class FtpDownloader {

    fun downloadAllJsonFiles(
        host: String,
        username: String,
        password: String,
        remotePath: String,
        localDirectoryPath: String,
        onResult: (String) -> Unit) {

        CoroutineScope(Dispatchers.IO).launch {

            val ftpClient = FTPClient().apply {
                connect(host)
                if (!login(username, password)) {
                    onResult("Login failed")
                    disconnect()
                    return@launch
                }
                enterLocalPassiveMode()
                setFileType(FTPClient.BINARY_FILE_TYPE)
            }

            try {
                ftpClient.listFiles(remotePath)
                    .filter { it.isFile && it.name.endsWith(".json") }
                    .forEach { file ->
                        val localFilePath = "$localDirectoryPath/${file.name}"
                        FileOutputStream(localFilePath).use { outputStream ->
                            if (!ftpClient.retrieveFile("$remotePath/${file.name}", outputStream))
                                onResult("Failed to download: ${file.name}")
                        }
                    }
                onResult("Download completed")
            } catch (e: Exception) {
                onResult("Error: ${e.localizedMessage}")
            } finally {
                ftpClient.logout()
                ftpClient.disconnect()
            }
        }
    }
}