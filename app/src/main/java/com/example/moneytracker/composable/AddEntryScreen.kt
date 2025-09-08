package com.example.moneytracker.composable


import android.widget.ScrollView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

enum class EntryType { Expense, Income }
data class Task(
    val title: String,
    val amount: Double,
    val date: String,
    val notes: String,
    val type: EntryType,
    val day: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryScreen(
    selectedOption: EntryType,
    onOptionSelected: (EntryType) -> Unit,
    selectedDay: String,
    onDaySelected: (String) -> Unit,
    onSave: (Task) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
    var showDatePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    Box(){
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
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
                value = date,
                readOnly = true,
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                ),
                onValueChange = { },
                placeholder = { Text("YYYY-MM-DD") },
                leadingIcon = {  IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Day of Week",
                modifier = Modifier.padding(bottom = 8.dp),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                days.forEach { day ->
                    val isSelected = day == selectedDay
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isSelected) Color.Black else Color.Gray)
                            .clickable { onDaySelected(day) }
                            .padding(horizontal = 8.dp, vertical = 6.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day,
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            Spacer(Modifier.height(16.dp))

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
                    if (amount.isNotEmpty() && category.isNotEmpty() && date.isNotEmpty()) {
                        onSave(
                            Task(
                                title = category,
                                amount = amount.toDouble(),
                                date = date,
                                notes = notes,
                                type = selectedOption,
                                day = selectedDay
                            )
                        )
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
        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePickerModal(
                        onDateSelected = { selectedDate ->
                            date = selectedDate?.let { convertMillisToDate(it) } ?: ""
                        },
                        datePickerState = datePickerState,
                        onDismiss = { showDatePicker = false }
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    datePickerState: DatePickerState,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
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

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}

@Preview(showBackground = true)
@Composable
fun AddEntryPreview() {
    AddEntryScreen(
        selectedOption = EntryType.Expense,
        onOptionSelected = {},
        selectedDay = "Mon",
        onDaySelected = {},
        onSave = {}
    )


}