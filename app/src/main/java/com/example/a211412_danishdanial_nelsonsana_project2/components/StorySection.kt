package com.example.a211412_danishdanial_nelsonsana_project2.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.a211412_danishdanial_nelsonsana_project2.ui.theme.primaryLight
import com.example.a211412_danishdanial_nelsonsana_project2.R

@Composable
fun StorySection() {

    val images = listOf(
        R.drawable.pic_1,
        R.drawable.pic_2,
        R.drawable.pic_3,
        R.drawable.pic_4,
        R.drawable.pic_5
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(end = 16.dp)
    ) {
        items(images) { img ->
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .border(2.dp, primaryLight, CircleShape)
                    .padding(3.dp)
            ) {
                Image(
                    painter = painterResource(id = img),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize().clip(CircleShape)
                )
            }
        }
    }
}