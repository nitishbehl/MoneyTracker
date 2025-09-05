package com.example.moneytracker.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IncomeDao {

    @Insert
    suspend fun insertIncome(income: IncomeEntity)

    @Query("DELETE FROM Income WHERE id = :id")
    suspend fun deleteIncomeById(id: Int)

    @Query("UPDATE Income SET name = :name, amount = :amount, date = :date, category = :category WHERE id = :id")
    suspend fun updateIncome(id: Int, name: String, amount: Double, date: String, category: String)

    @Query("SELECT * FROM Income")
    suspend fun getAllIncomes(): List<IncomeEntity>

    @Query("SELECT SUM(amount) FROM Income")
    suspend fun getTotalIncome(): Double


    @Query("DELETE FROM Income")
    suspend fun deleteAllIncomes()




}


