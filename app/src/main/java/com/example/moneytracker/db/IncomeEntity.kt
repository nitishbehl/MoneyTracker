package com.example.moneytracker.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Income")
data class IncomeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val amount: Double,
    val date: String,
    val category: String
)
