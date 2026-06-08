package com.example.a211412_danishdanial_nelsonsana_project2.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a211412_danishdanial_nelsonsana_project2.model.NearItem
import com.example.a211412_danishdanial_nelsonsana_project2.ui.theme.primaryLight

@Composable
fun StoreCard(item: NearItem, modifier: Modifier = Modifier, onClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .padding(bottom = 16.dp)
            .clickable { onClick() }
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )

            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = item.title, fontWeight = FontWeight.Bold)

                Text(
                    text = if (expanded) "Show less" else "Show more",
                    fontSize = 12.sp,
                    color = primaryLight,
                    modifier = Modifier.clickable { expanded = !expanded }
                )

                // 3. Conditional content that appears when clicked
                if (expanded) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = item.description,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Text(text = item.price, color = primaryLight, fontWeight = FontWeight.Bold)
                Text(text = item.storeName, fontSize = 12.sp, color = Color.Gray)
            }
        }
    }
}