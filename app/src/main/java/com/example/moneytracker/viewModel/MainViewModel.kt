package com.example.moneytracker.viewModel


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneytracker.db.AppDatabase
import com.example.moneytracker.db.TargetEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(val db: AppDatabase) : ViewModel() {
    var monthlyTargetState: MutableState<String> = mutableStateOf("")
    var dailyTargetState: MutableState<String> = mutableStateOf("")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val target = db.targetDao().getTarget()
            withContext(Dispatchers.Main) {
                monthlyTargetState.value = target?.monthlyTarget?.toString() ?: ""
                dailyTargetState.value = target?.dailyTarget?.toString() ?: ""
            }
        }
    }

    fun saveTarget(monthlyTarget: Double, dailyTarget: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val target = TargetEntity(monthlyTarget = monthlyTarget, dailyTarget = dailyTarget)
            db.targetDao().insertTarget(target)
            withContext(Dispatchers.Main) {
                monthlyTargetState.value = monthlyTarget.toString()
                dailyTargetState.value = dailyTarget.toString()
            }
        }
    }

    fun updateTarget(monthlyTarget: Double, dailyTarget: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val target = TargetEntity(monthlyTarget = monthlyTarget, dailyTarget = dailyTarget)
            db.targetDao().updateTarget(target)
            withContext(Dispatchers.Main) {
                monthlyTargetState.value = monthlyTarget.toString()
                dailyTargetState.value = dailyTarget.toString()
            }
        }
    }

    fun deleteTarget() {
        viewModelScope.launch(Dispatchers.IO) { db.targetDao().deleteTarget() }
    }


}
