package com.example.a211412_danishdanial_nelsonsana_project2.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {

    @Insert
    suspend fun insertBooking(booking: BookingEntity)

    @Query("SELECT * FROM booking_table")
    fun getAllBookings(): Flow<List<BookingEntity>>

    @Query("DELETE FROM booking_table")
    suspend fun deleteAllBookings()
}