package com.example.moneytracker.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.moneytracker.composable.AddExpense
import com.example.moneytracker.composable.AddIncome
import com.example.moneytracker.composable.EntryType
import com.example.moneytracker.composable.HomePageScreen
import com.example.moneytracker.composable.SetTargetScreen
import com.example.moneytracker.db.AppDatabase
import com.example.moneytracker.ui.theme.MoneyTrackerTheme
import com.example.moneytracker.viewModel.MainViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "money-tracker.db"
        ).fallbackToDestructiveMigration().build()

        val viewModel = MainViewModel(db)
        setContent {
            MoneyTrackerTheme {
                var showHome by remember { mutableStateOf(false) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        var showBottomSheet by remember { mutableStateOf(false) }

                        if (showHome) {
                            val monthly = viewModel.userFinancialState.value.monthlyTarget
                            val daily = viewModel.userFinancialState.value.dailyTarget
                            HomePageScreen(monthly, daily) {
                                showBottomSheet = true
                            }
                            if (showBottomSheet) {
                                BottomsSheetUI(){
                                    showBottomSheet = false
                                }
                            }
                        } else {
                            SetTargetScreen(
                                onSkip = { showHome = true },
                                onSave = { monthlyTarget, dailyTarget ->
                                    viewModel.saveTarget(monthlyTarget, dailyTarget)
                                    showHome = true
                                })
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomsSheetUI(hideBottomSheet:()->Unit) {
        val sheetState = rememberModalBottomSheetState()
        var selectedEntryType by remember { mutableStateOf(EntryType.Expense) }
        ModalBottomSheet(
            onDismissRequest = { hideBottomSheet() },
            sheetState = sheetState,
            containerColor = Color.White,
        ) {
            Surface(
                color = Color.White, contentColor = Color(0xFF0F172A)
            ) {
                when (selectedEntryType) {
                    EntryType.Expense -> AddExpense(
                        selectedOption = selectedEntryType, onOptionSelected = {
                            selectedEntryType = it
                        })

                    EntryType.Income -> AddIncome(
                        selectedOption = selectedEntryType,
                        onOptionSelected = {
                            selectedEntryType = it
                        },
                        saveIncome = { amount, date, notes ->
                            hideBottomSheet()
//                            addIncome(amount, date, notes)
                        })
                }
            }
        }
    }
}
