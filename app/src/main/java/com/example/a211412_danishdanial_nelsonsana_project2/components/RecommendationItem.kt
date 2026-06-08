package com.example.a211412_danishdanial_nelsonsana_project2.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun RecommendationItem(
    shopName: String,
    foodName: String,
    imageRes: Int,
    description: String,
    onClick: () -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .animateContentSize(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Row {
                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )

                Column {
                    Text(foodName)
                    Text("from $shopName")

                    Text(
                        if (expanded) "Show less" else "Show more",
                        modifier = Modifier.clickable { expanded = !expanded }
                    )
                }
            }

            if (expanded) {
                Text(description)
            }
        }
    }
}