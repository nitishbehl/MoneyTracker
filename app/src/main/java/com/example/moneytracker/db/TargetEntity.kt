package com.example.moneytracker.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Target")
data class TargetEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val monthlyTarget: Double,
    val dailyTarget: Double
)
