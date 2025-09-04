package com.example.moneytracker.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageScreen(
    monthly: Double,
    entries: List<Task>,
    daily: Double,
    onAddClick: () -> Unit = {},
) {

    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    var selectedDay by remember { mutableStateOf("Mon") }
    val money: (Double) -> String = { String.format("%.2f", it) }
    val incomeList = entries.filter { it.type == EntryType.Income }
    val expenseList = entries.filter { it.type == EntryType.Expense }
    val totalIncome = incomeList.sumOf { it.amount }
    val totalExpense = expenseList.sumOf { it.amount }

    Scaffold(
        containerColor = Color(0xFFF7F8FB), floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text("Add Task") },
                icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                onClick = { onAddClick() })
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                "Overview",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(top = 8.dp),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold, color = Color(0xFF0F172A)
                )
            )

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val targets = listOf(
                    CardData(
                        "Monthly Target",
                        money(monthly),
                        "Left ${money(monthly)}",
                        Icons.Filled.CalendarMonth
                    ), CardData(
                        "Daily Target",
                        money(daily),
                        "Left ${money(daily)}",
                        Icons.Filled.EventAvailable
                    )
                )
                targets.forEach { item ->
                    CardUi(
                        data = item, modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                val totalMoney = listOf(
                    TotalMoney(
                        "Total Income",
                        "$${money(totalIncome)}",
                        Icons.AutoMirrored.Filled.EventNote
                    ),
                    TotalMoney(
                        "Total Expense",
                        "$${money(totalExpense)}",
                        Icons.AutoMirrored.Filled.EventNote
                    )
                )
                totalMoney.forEach { item ->
                    TotalMoneyUi(
                        data = item, modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                days.forEach { day ->
                    val isSelected = day == selectedDay
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) Color(0xFF5B47FF) else Color(0xFFF3F5F9))
                            .clickable { selectedDay = day }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center) {
                        Text(
                            text = day,
                            color = if (isSelected) Color.White else Color(0xFF334155),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Income Sources",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0F172A)
            )
            Spacer(Modifier.height(12.dp))
            if (incomeList.isNotEmpty()) {
                incomeList.forEach { income ->
                    CardScreen(
                        title = income.title,
                        dateLabel = income.date,
                        amount = income.amount.toString(),
                        isIncome = true
                    )
                    Spacer(Modifier.height(12.dp))
                }
                Spacer(Modifier.height(12.dp))
            }

            Text(
                "Expense Details",
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF0F172A)
            )
            Spacer(Modifier.height(12.dp))
            if (expenseList.isNotEmpty()) {
                expenseList.forEach { expense ->
                    CardScreen(
                        title = expense.title,
                        dateLabel = expense.date,
                        amount = expense.amount.toString(),
                        isIncome = false
                    )
                    Spacer(Modifier.height(12.dp))
                }
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}


@Preview
@Composable
fun HomePageScreenPreview() {
    val Entries = listOf(
        Task("Salary", 2000.0, "2025-09-04", "Monthly salary", EntryType.Income),
        Task("Freelance", 500.0, "2025-09-03", "Project payment", EntryType.Income),
        Task("Food", 150.0, "2025-09-04", "Lunch", EntryType.Expense),
        Task("Transport", 50.0, "2025-09-04", "Bus pass", EntryType.Expense)
    )
    HomePageScreen(
        monthly = 3000.0,
        daily = 100.0,
        entries = Entries
    )


}
