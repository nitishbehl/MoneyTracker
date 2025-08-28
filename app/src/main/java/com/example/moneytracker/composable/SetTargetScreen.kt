package com.example.moneytracker.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moneytracker.viewModel.MainViewModel

@Composable
fun SetTargetScreen(
    viewModel: MainViewModel = viewModel(),
    onSkip: () -> Unit = {},
    onSave: () -> Unit = {},
) {
    var monthlyTarget by remember { mutableStateOf("") }
    var dailyTarget by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
    ) {

        Text(
            text = "Set Your Target", modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 28.sp,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
        )
        Spacer(Modifier.height(6.dp))
        Text(
            text = "Enter your monthly and daily targets to start tracking.",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
        )
        Spacer(Modifier.height(18.dp))
        Text(
            text = "Monthly Target",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            value = monthlyTarget,
            onValueChange = { monthlyTarget = it },
            label = { Text("Enter Monthly Target", color = Color.Black.copy(alpha = 0.5f)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
        )
        Spacer(Modifier.height(18.dp))
        Text(
            text = "Daily Target",
            color = Color.Black,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(10.dp))
        OutlinedTextField(
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            value = dailyTarget,
            onValueChange = { dailyTarget = it },
            label = { Text("Enter Daily Target", color = Color.Black.copy(alpha = 0.5f)) },
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(Modifier.weight(1f))
        IconButton(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF90CAF9), shape = MaterialTheme.shapes.medium),
        ) {
            Text(
                text = "Save and Continue",
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.clickable(
                    onClick = {
                        viewModel.saveTarget(
                            monthlyTarget.toDouble(),
                            dailyTarget.toDouble()
                        )
                        onSave()
                    }
                )
            )
        }
        Spacer(Modifier.height(8.dp))
        Text(
            "Skip for Now",
            color = Color.Black.copy(alpha = 0.5f),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {
                        onSkip()
                    }
                )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun SetTargetScreenPreview() {
    SetTargetScreen()
}