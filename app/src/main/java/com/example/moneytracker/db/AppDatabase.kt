
package com.example.moneytracker.db
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TargetEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun targetDao(): TargetDao
}
