package com.example.moneytracker.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TargetEntity::class, ExpenseEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun targetDao(): TargetDao

}
