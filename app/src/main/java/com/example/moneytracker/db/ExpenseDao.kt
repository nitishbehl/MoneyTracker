package com.example.moneytracker.db


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {
    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<ExpenseEntity>

    @Query("SELECT * FROM expenses WHERE date = :date")
    suspend fun getExpensesByDate(date: String): List<ExpenseEntity>
    @Query("SELECT * FROM expenses WHERE category = :category")
    suspend fun getExpensesByCategory(category: String): List<ExpenseEntity>

    @Query("DELETE FROM expenses")
    suspend fun deleteAllExpenses()

    @Query("DELETE FROM expenses WHERE id = :id")
    suspend fun deleteExpenseById(id: Int)

    @Query("UPDATE expenses SET name = :name, amount = :amount, date = :date, category = :category WHERE id = :id")
    suspend fun updateExpense(id: Int, name: String, amount: Double, date: String, category: String)

    @Query("SELECT SUM(amount) FROM expenses WHERE category = :category")
    suspend fun getTotalExpenseByCategory(category: String): Double

    @Query("SELECT SUM(amount) FROM expenses")
    suspend fun getTotalExpense(): Double

}