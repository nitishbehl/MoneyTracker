package com.example.moneytracker.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class TotalMoney(
    val title: String,
    val amount: String,
    val icon: ImageVector
)

@Composable
fun TotalMoneyUi(
    data: TotalMoney,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = data.title,
                    modifier = Modifier.weight(1f),
                    color = Color(0xFF334155),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                )
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(0xFFF3F5F9)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = data.icon,
                        contentDescription = null,
                        tint = Color(0xFF0F172A)
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = data.amount,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF10B981)
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TotalMoneyUiPreview() {
    TotalMoneyUi(
        data = TotalMoney(
            "Total Balance",
            "$8,000.00",
            Icons.AutoMirrored.Filled.EventNote
        ),

    )
}

