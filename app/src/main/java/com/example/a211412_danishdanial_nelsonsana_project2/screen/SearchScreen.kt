package com.example.a211412_danishdanial_nelsonsana_project2.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a211412_danishdanial_nelsonsana_project2.R
import com.example.a211412_danishdanial_nelsonsana_project2.components.StoreCard
import com.example.a211412_danishdanial_nelsonsana_project2.model.NearItem
import com.example.a211412_danishdanial_nelsonsana_project2.ui.theme.Project2Theme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, initialQuery: String) {
    var searchQuery by remember { mutableStateOf(if (initialQuery == "all") "" else initialQuery) }

    // Combined list for search results
    val itemsList = listOf(
        NearItem("Nasi Lemak", "25 left", "Warung", R.drawable.pic_nl, "Nice food"),
        NearItem("Burger", "10 left", "Abang", R.drawable.pic_2, "Juicy burger"),
        NearItem("Fried Rice", "15 left", "Uncle Lim", R.drawable.pic_1, "Fragrant fried rice"),
        NearItem("Cake Slice", "5 left", "Sweet Treats", R.drawable.pic_dessert, "Chocolate fudge cake")
    )

    val filtered = itemsList.filter {
        it.title.contains(searchQuery, true) || it.storeName.contains(searchQuery, true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Search stores and food") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp),
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(8.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (searchQuery.isNotBlank()) {
                Text(
                    text = "Results for \"$searchQuery\"",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filtered) { item ->
                    StoreCard(item, Modifier.fillMaxWidth()) {
                        navController.navigate("details/${item.title}")
                    }
                }

                if (filtered.isEmpty()) {
                    item {
                        Box(modifier = Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No stores or food found matching \"$searchQuery\"", color = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    Project2Theme {
        SearchScreen(
            navController = rememberNavController(),
            initialQuery = "Nasi"
        )
    }
}
