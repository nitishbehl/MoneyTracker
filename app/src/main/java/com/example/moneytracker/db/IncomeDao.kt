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

    @Query("DELETE FROM Income WHERE id = :id")
    suspend fun deleteIncomeById(id: Int)

    @Query("UPDATE Income SET name = :name, amount = :amount, date = :date WHERE id = :id")
    suspend fun updateIncome(id: Int, name: String, amount: Double, date: String)

    @Query("SELECT SUM(amount) FROM Income")
    suspend fun getTotalIncome(): Double

    @Query("SELECT SUM(amount) FROM Income WHERE category = :category")
    suspend fun getTotalIncomeByCategory(category: String): Double

    @Query("SELECT * FROM Income WHERE category = :category")
    suspend fun getIncomesByCategory(category: String): List<IncomeEntity>

    @Query("SELECT * FROM Income WHERE category = :category AND date = :date")
    suspend fun getIncomesByCategoryAndDate(category: String, date: String): List<IncomeEntity>
}
