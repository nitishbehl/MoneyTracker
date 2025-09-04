package com.example.moneytracker.composable


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.concurrent.timerTask

enum class EntryType { Expense, Income }
data class Task(
    val title: String,
    val amount: Double,
    val date: String,
    val notes: String,
    val type: EntryType
)

@Composable
fun AddEntryScreen(
    selectedOption: EntryType,
    onOptionSelected: (EntryType) -> Unit,
    onSave: (Task) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(20.dp)
            .background(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (selectedOption == EntryType.Income) "Add Income" else "Add Expense",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f)
            )
            ExpenseIncomeToggle(
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected
            )
        }

        Spacer(Modifier.height(18.dp))


        Text(
            "Amount",
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )

        OutlinedTextField(
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            value = amount,
            onValueChange = { amount = it },
            placeholder = { Text("$0.0", color = Color.Black.copy(alpha = 0.5f)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))


        Text(
            "Category",
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        )

        OutlinedTextField(
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            value = category,
            onValueChange = { category = it },
            placeholder = { Text("Type or select category") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))


        Text(
            "Date",
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            color = Color.Black
        )
        OutlinedTextField(
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            value = date,
            onValueChange = { date = it },
            placeholder = { Text("YYYY-MM-DD") },
            leadingIcon = { Icon(Icons.Default.CalendarMonth, null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        Text(
            "Notes (optional)",
            modifier = Modifier.padding(bottom = 8.dp),
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        OutlinedTextField(
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            value = notes,
            onValueChange = { notes = it },
            placeholder = { Text("e.g. Uber ride") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(22.dp))


        IconButton(
            onClick = {
                if (amount.isNotEmpty() && category.isNotEmpty()) {
                    onSave(Task(category, amount.toDouble(), date, notes, selectedOption))
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF90CAF9), shape = MaterialTheme.shapes.medium),
        ) {
            Text(
                text = "Save",
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            "Clear",
            color = Color(0xFF6B7280),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ExpenseIncomeToggle(selectedOption: EntryType, onOptionSelected: (EntryType) -> Unit) {
    val options = listOf(EntryType.Expense, EntryType.Income)

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(Color(0xFFF2F2F2))
            .padding(4.dp)
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(if (isSelected) Color.White else Color.Transparent)
                    .clickable { onOptionSelected(option) }
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = option.name,
                    color = if (isSelected) Color.Black else Color.Gray,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddEntryPreview() {
    AddEntryScreen(
        selectedOption = EntryType.Expense,
        onOptionSelected = {},
        onSave = { task ->

        }
    )


}