package com.example.a211412_danishdanial_nelsonsana_project2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "booking_table")
data class BookingEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val foodName: String,
    val userName: String,
    val referenceNumber: String
)