package com.example.a211412_danishdanial_nelsonsana_project2.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.a211412_danishdanial_nelsonsana_project2.R
import com.example.a211412_danishdanial_nelsonsana_project2.model.NearItem

@Composable
fun RecommendationSection(navController: NavController) {
    val recommendations = listOf(
        NearItem("Nasi Goreng", "15 left", "Roni Juara", R.drawable.pic_nasgor, "Fried rice is a flavorful dish made by stir-frying cooked rice with ingredients like eggs, vegetables, meat or seafood, and seasoned with soy sauce or spices. It’s a versatile and quick meal commonly enjoyed across many cultures."),
        NearItem("Cake Slice", "5 left", "Sweet Treats", R.drawable.pic_cake, "Cake is a sweet baked dessert made from ingredients like flour, sugar, eggs, and butter, often flavored with vanilla or chocolate and decorated with icing or cream. It’s commonly enjoyed during celebrations and special occasions.")
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(recommendations) { item ->
            StoreCard(item, Modifier.width(200.dp)) {
                navController.navigate("details/${item.title}")
            }
        }
    }
}