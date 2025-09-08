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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.room.Room
import com.example.moneytracker.composable.AddEntryScreen
import com.example.moneytracker.composable.EntryType
import com.example.moneytracker.composable.HomePageScreen
import com.example.moneytracker.composable.SetTargetScreen
import com.example.moneytracker.composable.Task
import com.example.moneytracker.db.AppDatabase
import com.example.moneytracker.ui.theme.MoneyTrackerTheme
import com.example.moneytracker.viewModel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "money-tracker.db"
        ).fallbackToDestructiveMigration()
            .build()

        val viewModel = MainViewModel(db)

        setContent {
            MoneyTrackerTheme {
                var showBottomSheet by remember { mutableStateOf(false) }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        if (viewModel.showHome.value) {
                            HomePageScreen(
                                monthly = viewModel.userFinancialState.value.monthlyTarget,
                                daily = viewModel.userFinancialState.value.dailyTarget,
                                incomeList = viewModel.incomeListState.value,
                                expenseList = viewModel.expenseListState.value,
                                selectedDay = viewModel.selectedDayState.value,
                                onDaySelected = { viewModel.setSelectedDay(it) },
                                onDeleteIncome = { viewModel.deleteIncome(it) },
                                onDeleteExpense = { viewModel.deleteExpense(it) },
                                onAddClick = { showBottomSheet = true }
                            )

                            if (showBottomSheet) {
                                BottomsSheetUI(
                                    onAddTask = { task ->
                                        if (task.type == EntryType.Expense) {
                                            viewModel.addExpense(
                                                task.title,
                                                task.amount,
                                                task.date,
                                                task.notes
                                            )
                                        } else {
                                            viewModel.addIncome(
                                                task.title,
                                                task.amount,
                                                task.date,
                                                task.notes
                                            )
                                        }
                                        showBottomSheet = false
                                    },
                                    selectedDay = viewModel.selectedDayState.value,
                                    onDaySelected = { viewModel.setSelectedDay(it) },
                                    hideBottomSheet = { showBottomSheet = false }
                                )
                            }
                        } else {
                            SetTargetScreen(
                                onSkip = { viewModel.showHome.value = true },
                                onSave = { monthlyTarget, dailyTarget ->
                                    viewModel.saveTarget(monthlyTarget, dailyTarget)
                                    viewModel.showHome.value = true

                                })
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomsSheetUI(
        selectedDay: String,
        onDaySelected: (String) -> Unit,
        onAddTask: (Task) -> Unit,
        hideBottomSheet: () -> Unit
    ) {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )
        var selectedEntryType by remember { mutableStateOf(EntryType.Expense) }

        LaunchedEffect(Unit) {
            sheetState.show()
        }
        ModalBottomSheet(
            onDismissRequest = { hideBottomSheet() },
            sheetState = sheetState,
            containerColor = Color.White,
        ) {
            Surface(
                color = Color.White, contentColor = Color(0xFF0F172A)
            ) {
                AddEntryScreen(
                    selectedOption = selectedEntryType,
                    onOptionSelected = { selectedEntryType = it },
                    selectedDay = selectedDay,
                    onDaySelected = onDaySelected,
                    onSave = onAddTask
                )
            }
        }
    }
}
