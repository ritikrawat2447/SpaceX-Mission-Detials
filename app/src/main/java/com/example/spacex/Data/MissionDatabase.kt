package com.example.spacex.Data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class MissionDatabase  : RoomDatabase() {
    abstract fun missionDao(): MissionDao

    companion object {
        @Volatile
        private var INSTANCE: MissionDatabase? = null

        fun getDatabase(context: Context): MissionDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MissionDatabase::class.java,
                    "mission_database"

                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}