package com.example.a211412_danishdanial_nelsonsana_project2.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.a211412_danishdanial_nelsonsana_project2.ui.theme.Project2Theme

@Composable
fun FoodDetailsScreen(navController: NavController, foodName: String, innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Food Details: $foodName",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "This is a delicious $foodName. Rescue it now to help reduce food waste!",
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            navController.navigate("booking/$foodName")
        }) {
            Text("Book Food")
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        TextButton(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailsScreenPreview() {
    Project2Theme {
        FoodDetailsScreen(
            navController = rememberNavController(),
            foodName = "Nasi Lemak",
            innerPadding = PaddingValues(0.dp)
        )
    }
}

