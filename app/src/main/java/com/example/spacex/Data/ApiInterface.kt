package com.example.spacex.Data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val retrofitBuilder = Retrofit.Builder()
    .baseUrl("https://api.spacexdata.com/v3/")
    .addConverterFactory(GsonConverterFactory.create()).build()

val missionService = retrofitBuilder.create(ApiInterface::class.java)

interface ApiInterface {
    @GET("launches")
    suspend fun getMissionDetails() : MissionDetails
}