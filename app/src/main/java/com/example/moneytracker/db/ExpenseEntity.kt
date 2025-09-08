package com.example.moneytracker.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Expense")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val amount: Double,
    val date: String,
    val category: String,
    val day: String
)
