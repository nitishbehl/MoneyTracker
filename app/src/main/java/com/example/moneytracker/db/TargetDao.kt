package com.example.moneytracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TargetDao {

    @Insert
    suspend fun insertTarget(target: TargetEntity)

    @Update
    suspend fun updateTarget(target: TargetEntity)

    @Query("SELECT * FROM Target WHERE id = :id")
    suspend fun getTargetById(id: Int): TargetEntity?

    @Query("DELETE FROM Target")
    suspend fun deleteAllTargets()
}
