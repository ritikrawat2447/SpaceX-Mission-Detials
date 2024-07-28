package com.example.spacex.AllScreens

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spacex.Data.MissionDetailsItem
import com.example.spacex.MissionViewModel
import com.example.spacex.R
import com.example.spacex.Screens
import com.google.gson.Gson

@Composable
fun HomeScreen(navController: NavController) {
    val missionViewModel: MissionViewModel = viewModel()
    val viewState by missionViewModel.missionState
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            viewState.loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            viewState.error != null -> {
                Text(
                    "ERROR OCCURRED ${viewState.error}",
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                MissionScree(missions = viewState.list, navController)
            }
        }
    }
}

@Composable
fun MissionScree(missions: List<MissionDetailsItem>, navController: NavController) {
    Spacer(modifier = Modifier.height(16.dp))
    LazyColumn {
        items(missions) { missions ->
            MissionView(missionDetailsItem = missions, navController)
        }
    }
}

@Composable
fun MissionView(missionDetailsItem: MissionDetailsItem, navController: NavController) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate(Screens.Detail.route + "/${missionDetailsItem.flight_number}")
            }

    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = missionDetailsItem.mission_name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Row {
                    Text(
                        text = "Rocket used is : ",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 13.sp
                    )
                    Text(
                        text = missionDetailsItem.rocket.rocket_name,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        fontSize = 13.sp
                    )
                }
                Row {
                    Text(
                        text = "Launch In : ",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        fontSize = 13.sp
                    )
                    Text(
                        text = missionDetailsItem.launch_year,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}