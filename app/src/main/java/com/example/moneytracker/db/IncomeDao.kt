package com.example.moneytracker.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface IncomeDao {

    @Insert
    suspend fun insertIncome(income: IncomeEntity)

    @Query("SELECT * FROM Income")
    suspend fun getAllIncomes(): List<IncomeEntity>

    @Query("SELECT * FROM Income WHERE date = :date")
    suspend fun getIncomesByDate(date: String): List<IncomeEntity>

    @Query("DELETE FROM Income")
    suspend fun deleteAllIncomes()


    @Query("UPDATE Income SET name = :name, amount = :amount, date = :date WHERE id = :id")
    suspend fun updateIncome(id: Int, name: String, amount: Double, date: String)

    @Query("SELECT SUM(amount) FROM Income")
    suspend fun getTotalIncome(): Double
}


