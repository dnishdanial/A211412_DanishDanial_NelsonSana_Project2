package com.example.a211412_danishdanial_nelsonsana_project2.model

// A no-argument constructor or default values are required for Firebase serialization
data class CommunityFoodItem(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val storeName: String = "Community Donor",
    val status: String = "Available"
)