package com.example.moneytracker.db


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Insert

@Dao
interface TargetDao {
    @Query("SELECT * FROM TargetEntity WHERE id = 1")
    suspend fun getTarget(): TargetEntity?

    @Update
    suspend fun updateTarget(target: TargetEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTarget(target: TargetEntity)

    @Delete
    suspend fun deleteTarget(target: TargetEntity)

    @Query("DELETE FROM TargetEntity WHERE id = 1")
    suspend fun deleteTarget()


}