package com.example.moneytracker.viewModel


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.db.AppDatabase
import com.example.moneytracker.db.ExpenseEntity
import com.example.moneytracker.db.IncomeEntity
import com.example.moneytracker.db.TargetEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class UserFinancialState(
    val userName: String = "",
    val monthlyTarget: Double = 0.0,
    val dailyTarget: Double = 0.0,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,

    )

class MainViewModel(val db: AppDatabase) : ViewModel() {

    val userFinancialState: MutableState<UserFinancialState> = mutableStateOf(UserFinancialState())
    val incomeListState: MutableState<List<IncomeEntity>> = mutableStateOf(emptyList())
    val expenseListState: MutableState<List<ExpenseEntity>> = mutableStateOf(emptyList())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val target = db.targetDao().getTargetById(1)
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

    fun addIncome(name: String, amount: Double, date: String, category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.incomeDao().insertIncome(
                IncomeEntity(
                    name = name,
                    amount = amount,
                    date = date,
                    category = category
                )
            )
            val totalIncome = db.incomeDao().getTotalIncome()
            withContext(Dispatchers.Main) {
                userFinancialState.value = userFinancialState.value.copy(
                    totalIncome = totalIncome
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

    fun deleteIncome(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            db.incomeDao().deleteIncomeById(id)
            val totalIncome = db.incomeDao().getTotalIncome()
            withContext(Dispatchers.Main) {
                userFinancialState.value = userFinancialState.value.copy(
                    totalIncome = totalIncome
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

    fun updateIncome(id: Int, name: String, amount: Double, date: String, category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            db.incomeDao().updateIncome(id, name, amount, date, category)
            val totalIncome = db.incomeDao().getTotalIncome()
            withContext(Dispatchers.Main) {
                userFinancialState.value = userFinancialState.value.copy(
                    totalIncome = totalIncome
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
            db.targetDao().deleteAllTargets()
            withContext(Dispatchers.Main) {
                userFinancialState.value = userFinancialState.value.copy(
                    monthlyTarget = 0.0,
                    dailyTarget = 0.0
                )
            }
        }
    }


}
