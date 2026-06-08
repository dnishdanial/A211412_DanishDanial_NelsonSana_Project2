package com.example.a211412_danishdanial_nelsonsana_project2.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a211412_danishdanial_nelsonsana_project2.R
import com.example.a211412_danishdanial_nelsonsana_project2.UserViewModel
import com.example.a211412_danishdanial_nelsonsana_project2.components.*
import com.example.a211412_danishdanial_nelsonsana_project2.ui.theme.Project2Theme
import com.example.a211412_danishdanial_nelsonsana_project2.ui.theme.primaryLight

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: UserViewModel,
    innerPadding: PaddingValues
) {
    var nameInput by remember(viewModel.userState.name) { mutableStateOf(viewModel.userState.name) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header Title
        item {
            Column {
                Text(
                    text = "Welcome to KASIH",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = primaryLight
                )
                Text(
                    text = "Hello, ${viewModel.userState.name}!",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        }

        // Name Verification Row
        if (!viewModel.userState.isVerified) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = nameInput,
                        onValueChange = { nameInput = it },
                        placeholder = { Text("Enter Name") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )
                    Button(
                        onClick = {
                            viewModel.updateName(nameInput)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = primaryLight),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.height(56.dp)
                    ) {
                        Text("Verify", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Search Bar
        item {
            Surface(
                onClick = { navController.navigate("search/all") },
                color = Color(0xFFFFE0D8),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Search stores and food",
                        color = Color.Gray,
                        fontSize = 16.sp
                    )
                }
            }
        }

        // Story Section
        item {
            StorySection()
        }

        // Hotspot Banner
        item {
            Card(
                // Trigger the navigation when the user taps anywhere on the banner
                onClick = { navController.navigate("map") },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    // Background Image
                    Image(
                        painter = painterResource(id = R.drawable.pic_hotspot),
                        contentDescription = "Lunch Bento Banner",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .drawWithContent {
                                drawContent()
                                drawRect(color = Color.Black.copy(alpha = 0.3f))
                            }
                    )

                    // Banner Text
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Free Food",
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "CLICK TO FIND HOTSPOT",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                    }
                }
            }
        }

        // Categories Section
        item {
            Column {
                Text(
                    text = "Categories",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CategoryItem("Free", R.drawable.pic_mystery)
                    CategoryItem("Dine-In", R.drawable.pic_dinein)
                    CategoryItem("Meals", R.drawable.pic_meals)
                    CategoryItem("Desserts", R.drawable.pic_dessert)
                }
            }
        }

        // Near to you Section
        item {
            Column {
                Text(
                    text = "Near to you",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                NearToYouSection("", navController)

                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Recommended for you",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                RecommendationSection(navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Project2Theme {
        HomeScreen(
            navController = rememberNavController(),
            viewModel = viewModel(),
            innerPadding = PaddingValues(0.dp)
        )
    }
}
