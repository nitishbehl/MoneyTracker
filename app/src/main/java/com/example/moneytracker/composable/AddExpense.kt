package com.example.moneytracker.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun AddExpense(
    selectedOption: EntryType,
    onOptionSelected: (EntryType) -> Unit,
) {
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    val categories = listOf("Salary", "Freelance", "Bonus", "Interest", "Refund", "Other")


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
                "Add Expense",
                fontSize = 28.sp,
                fontWeight = FontWeight.ExtraBold,
                modifier = Modifier.weight(1f)
            )
            ExpenseIncomeToggle(
                selectedOption = selectedOption,
                onOptionSelected = onOptionSelected
            )
            Spacer(Modifier.height(18.dp))
        }
        Text(
            text = "Amount",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(4.dp))
        OutlinedTextField(
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            value = amount,
            onValueChange = { amount = it },
            label = { Text("$0.0") },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "Category",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(10.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(categories.size) { index ->
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = MaterialTheme.shapes.large
                        )
                        .padding(horizontal = 12.dp, vertical = 8.dp)


                ) {
                    Text(
                        text = categories[index],
                        fontSize = 15.sp,
                        color = Color.Black,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Text("Date", fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            placeholder = { Text("YYYY-MM-DD") },
            leadingIcon = { Icon(Icons.Default.CalendarMonth, null) },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Text("Notes (optional)", fontWeight = FontWeight.SemiBold)
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = notes,
            onValueChange = { notes = it },
            placeholder = { Text("e.g. Client invoice #1024") },
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(22.dp))

        IconButton(
            onClick = { },
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
            modifier = Modifier
                .fillMaxWidth()

        )
    }
}

@Composable
@Preview(showBackground = true)
fun AddExpensePreview() {
    AddExpense(
        selectedOption = EntryType.Expense,
        onOptionSelected = {}
    )
}