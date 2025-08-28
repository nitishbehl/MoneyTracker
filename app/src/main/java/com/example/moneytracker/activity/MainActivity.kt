package com.example.moneytracker.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.moneytracker.composable.HomePageScreen
import com.example.moneytracker.composable.SetTargetScreen
import com.example.moneytracker.db.AppDatabase
import com.example.moneytracker.ui.theme.MoneyTrackerTheme
import com.example.moneytracker.viewModel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "target-database"
        ).build()

        val viewModel = MainViewModel(db)
        setContent {
            MoneyTrackerTheme {
                var showHome by remember { mutableStateOf(false) }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        if (showHome) {
                            HomePageScreen(viewModel = viewModel)
                        } else {
                            SetTargetScreen(
                                viewModel = viewModel,
                                onSkip = { showHome = true },
                                onSave = {  showHome = true }
                            )
                        }
                    }
                }
            }
        }
    }
}
