package com.example.spacex.Data

import kotlinx.coroutines.flow.Flow

class MissionRepository(private val missionDao : MissionDao) {
    suspend fun insertMissions(missions: List<RequiredMissionDeatils>) = missionDao.insertMissions(missions)
    suspend fun getAllMissions() = missionDao.getAllMissions()
}