package com.example.moneytracker.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip

@Composable
fun CardScreen(
    title: String,
    dateLabel: String,
    amount: String,
    isIncome: Boolean = true,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0xFF1C1C1E), CircleShape)
                    .clickable {
                        onDelete()
                    })

            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Default.Work,
                contentDescription = "Salary",
                tint = Color(0xFF1C1C1E),
                modifier = Modifier.size(32.dp)
            )

            Spacer(Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF1C1C1E)
                    )
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = dateLabel,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 14.sp,
                        color = Color(0xFF6B7280)
                    )
                )
            }

            Text(
                text = amount,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isIncome) Color(0xFF16A34A) else Color(0xFFDC2626)
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardScreenPreview() {
    CardScreen(
        title = "Salary",
        dateLabel = "Aug 18, 2025",
        amount = "$1,000.00",
        isIncome = true,
        onDelete = {}
    )
}
