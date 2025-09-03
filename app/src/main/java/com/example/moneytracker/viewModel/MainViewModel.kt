package com.example.moneytracker.viewModel


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.db.AppDatabase
import com.example.moneytracker.db.ExpenseEntity
import com.example.moneytracker.db.TargetEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class UserFinancialState(
    val userName: String = "",
    val monthlyTarget: Double = 0.0,
    val dailyTarget: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0
)

class MainViewModel(val db: AppDatabase) : ViewModel() {

    val userFinancialState = mutableStateOf(UserFinancialState())
    val expenses: MutableState<List<ExpenseEntity>> = mutableStateOf(emptyList())
    val totalExpense: MutableState<Double> = mutableStateOf(0.0)
    val totalIncome: MutableState<Double> = mutableStateOf(0.0)


    init {
        viewModelScope.launch(Dispatchers.IO) {
            val target = db.targetDao().getTarget()
            withContext(Dispatchers.Main) {
                userFinancialState.value = userFinancialState.value.copy(
                    monthlyTarget = target?.monthlyTarget ?: 0.0,
                    dailyTarget = target?.dailyTarget ?: 0.0
                )
            }
        }
    }

    fun addExpense(name: String, amount: Double, date: String, category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.expenseDao().insertExpense(
                ExpenseEntity(
                    name = name,
                    amount = amount,
                    date = date,
                    category = category
                )
            )
            val totalExpense = db.expenseDao().getTotalExpense()
            withContext(Dispatchers.Main) {
                userFinancialState.value = userFinancialState.value.copy(
                    totalExpense = totalExpense
                )
            }
        }
    }

    fun deleteExpense(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            db.expenseDao().deleteExpenseById(id)
            val totalExpense = db.expenseDao().getTotalExpense()
            withContext(Dispatchers.Main) {
                userFinancialState.value = userFinancialState.value.copy(
                    totalExpense = totalExpense
                )
            }
        }
    }


    fun updateExpense(id: Int, name: String, amount: Double, date: String, category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.expenseDao().updateExpense(id, name, amount, date, category)
            val totalExpense = db.expenseDao().getTotalExpense()
            withContext(Dispatchers.Main) {
                userFinancialState.value = userFinancialState.value.copy(
                    totalExpense = totalExpense
                )
            }
        }
    }


    fun saveTarget(monthlyTarget: Double, dailyTarget: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val target = TargetEntity(monthlyTarget = monthlyTarget, dailyTarget = dailyTarget)
            db.targetDao().insertTarget(target)
            withContext(Dispatchers.Main) {
                userFinancialState.value = userFinancialState.value.copy(
                    monthlyTarget = monthlyTarget,
                    dailyTarget = dailyTarget
                )
            }
        }
    }

    fun updateTarget(monthlyTarget: Double, dailyTarget: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val target = TargetEntity(monthlyTarget = monthlyTarget, dailyTarget = dailyTarget)
            db.targetDao().updateTarget(target)
            withContext(Dispatchers.Main) {
                userFinancialState.value = userFinancialState.value.copy(
                    monthlyTarget = monthlyTarget,
                    dailyTarget = dailyTarget
                )
            }
        }
    }

    fun deleteTarget() {
        viewModelScope.launch(Dispatchers.IO) {
            db.targetDao().deleteTarget()
            withContext(Dispatchers.Main) {
                userFinancialState.value = userFinancialState.value.copy(
                    monthlyTarget = 0.0,
                    dailyTarget = 0.0
                )
            }
        }
    }

}
