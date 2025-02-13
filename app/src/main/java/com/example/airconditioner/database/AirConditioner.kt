package com.example.airconditioner.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airConditioner")
data class AirConditioner(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val managerCd: String,
    val manager: String,
    val temperature: String,
    val isAirConditionerClean: Boolean,
    val isFanClean: Boolean,
    val isIllumination: Boolean,
    val outputFlg: Int
)
