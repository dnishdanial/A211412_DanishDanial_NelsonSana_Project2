package com.example.a211412_danishdanial_nelsonsana_project2.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import com.example.a211412_danishdanial_nelsonsana_project2.ui.theme.primaryLight

@Composable
fun CategoryItem(name: String, iconRes: Int) {
    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = Color.White,
            modifier = Modifier
                .size(60.dp)
                .border(1.dp, Color.LightGray, CircleShape)
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }
        Text(name, fontSize = 12.sp)
    }
}
