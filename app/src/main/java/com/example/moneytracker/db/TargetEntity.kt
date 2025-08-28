package com.example.moneytracker.db


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TargetEntity(
    @PrimaryKey val id: Int = 1,
    val monthlyTarget: Double,
    val dailyTarget: Double
)