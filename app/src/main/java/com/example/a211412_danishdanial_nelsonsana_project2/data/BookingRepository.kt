package com.example.a211412_danishdanial_nelsonsana_project2.data

import kotlinx.coroutines.flow.Flow

class BookingRepository(
    private val bookingDao: BookingDao
) {

    val allBookings: Flow<List<BookingEntity>> =
        bookingDao.getAllBookings()

    suspend fun insertBooking(booking: BookingEntity) {
        bookingDao.insertBooking(booking)
    }

    suspend fun deleteAllBookings() {
        bookingDao.deleteAllBookings()
    }
}