package com.example.a211412_danishdanial_nelsonsana_project2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BookingEntity::class],
    version = 1
)
abstract class KasihDatabase : RoomDatabase() {

    abstract fun bookingDao(): BookingDao

    companion object {

        @Volatile
        private var INSTANCE: KasihDatabase? = null

        fun getDatabase(context: Context): KasihDatabase {

            return INSTANCE ?: synchronized(this) {

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KasihDatabase::class.java,
                    "kasih_database"
                ).build()

                INSTANCE = instance
                instance
            }
        }
    }
}