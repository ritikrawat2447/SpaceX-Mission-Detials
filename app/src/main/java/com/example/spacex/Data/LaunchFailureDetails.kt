package com.example.spacex.Data

data class LaunchFailureDetails(
    val altitude: Int,
    val reason: String,
    val time: Int
)