package com.example.moneytracker.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Preview
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CardData(
    val title: String,
    val amount: String,
    val leftText: String,
    val icon: ImageVector,
    val onEditClick: (() -> Unit)? = null
)

@Composable
fun CardUi(
    data: CardData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Top row: Icon + Edit button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
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

                if (data.onEditClick != null) {
                    IconButton(onClick = { data.onEditClick.invoke() }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit target",
                            tint = Color(0xFF5B47FF)
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Text content
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    data.title,
                    color = Color(0xFF334155),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    data.amount,
                    color = Color(0xFF0F172A),
                    fontSize = 26.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    data.leftText,
                    color = Color(0xFF64748B),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CardUiPreview() {
    CardUi(
        data = CardData(
            "Monthly Target",
            "$8,000.00",
            "Left $910.72",
            Icons.Filled.Preview,
            onEditClick = {}
        )
    )
}

