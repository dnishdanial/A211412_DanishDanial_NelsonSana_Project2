package com.example.a211412_danishdanial_nelsonsana_project2.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.a211412_danishdanial_nelsonsana_project2.model.NearItem
import com.example.a211412_danishdanial_nelsonsana_project2.R

@Composable
fun NearToYouSection(searchQuery: String, navController: NavController) {

    val itemsList = listOf(
        NearItem("Nasi Lemak", "25 left", "Warung Burung Hantu", R.drawable.pic_nl, "Nasi lemak is a traditional Malaysian dish made of fragrant rice cooked in coconut milk, typically served with spicy sambal, fried anchovies, peanuts, boiled egg, and cucumber. It’s a popular meal enjoyed for breakfast but can be eaten any time of the day."),
        NearItem("Burger", "10 left", "Abang Lan", R.drawable.pic_burger, "A burger is a popular sandwich made with a cooked patty (usually beef, chicken, or plant-based) placed inside a bun, often topped with lettuce, tomato, cheese, and sauces like ketchup or mayonnaise. It’s a quick, satisfying meal enjoyed worldwide.") ,
        NearItem("Nasi Kerabu", "3 left", "Moknab", R.drawable.pic_nk, "Nasi kerabu is a traditional Malaysian dish known for its vibrant blue rice, colored naturally with butterfly pea flowers. It’s typically served with herbs, vegetables, salted egg, fish or chicken, and a flavorful mix of sambal and coconut-based condiments."),
        NearItem("Laksa Utaqa", "20 left", "3Brother", R.drawable.pic_laksa, "Laksa is a popular Southeast Asian noodle soup, especially loved in Malaysia, made with rice noodles in a rich, spicy broth—either coconut milk-based or sour tamarind-based—often topped with shrimp, chicken, tofu, and fresh herbs.")

    )


    val filtered = itemsList.filter {
        it.title.contains(searchQuery, true)
    }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(filtered) { item ->
            StoreCard(item, Modifier.width(250.dp)) {
                navController.navigate("details/${item.title}")
            }
        }
    }
}