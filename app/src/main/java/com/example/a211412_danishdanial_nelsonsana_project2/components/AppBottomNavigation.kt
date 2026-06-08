package com.example.a211412_danishdanial_nelsonsana_project2.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.a211412_danishdanial_nelsonsana_project2.ui.theme.primaryLight

@Composable
fun AppBottomNavigation(navController: NavController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val current = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = primaryLight,
        contentColor = Color.White
    ) {

        NavigationBarItem(
            selected = current == "home",
            onClick = { navController.navigate("home") },
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.7f),
                unselectedTextColor = Color.White.copy(alpha = 0.7f),
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = current == "add",
            onClick = { navController.navigate("add") },
            icon = { Icon(Icons.Default.AddCircle, null) },
            label = { Text("Add") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.7f),
                unselectedTextColor = Color.White.copy(alpha = 0.7f),
                indicatorColor = Color.Transparent
            )
        )

        NavigationBarItem(
            selected = current == "profile",
            onClick = { navController.navigate("profile") },
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Profile") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.White.copy(alpha = 0.7f),
                unselectedTextColor = Color.White.copy(alpha = 0.7f),
                indicatorColor = Color.Transparent
            )
        )
    }
}