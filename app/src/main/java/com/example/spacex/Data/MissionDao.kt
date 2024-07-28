package com.example.spacex.Data

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

abstract class MissionDao() {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMissions(missions: List<RequiredMissionDeatils>)

    @Query("SELECT * FROM missions")
    abstract fun getAllMissions(): List<RequiredMissionDeatils>


}