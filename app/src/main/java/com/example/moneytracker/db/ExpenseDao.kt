package com.example.moneytracker.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM Expense")
    suspend fun getAllExpenses(): List<ExpenseEntity>

    @Query("SELECT * FROM Expense WHERE date = :date")
    suspend fun getExpensesByDate(date: String): List<ExpenseEntity>


    @Query("DELETE FROM Expense WHERE id = :id")
    suspend fun deleteExpenseById(id: Int)

    @Query("DELETE FROM Expense")
    suspend fun deleteAllExpenses()


    @Query("UPDATE Expense SET name = :name, amount = :amount, date = :date, category = :category WHERE id = :id")
    suspend fun updateExpense(id: Int, name: String, amount: Double, date: String, category: String)

    @Query("SELECT SUM(amount) FROM Expense")
    suspend fun getTotalExpense(): Double
}
