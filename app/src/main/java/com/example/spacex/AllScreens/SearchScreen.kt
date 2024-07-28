package com.example.spacex.AllScreens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.spacex.MissionViewModel

@Composable
fun SearchScreen( navController: NavController){
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
                StoreView(navController = navController)
            }
        }
    }
    
}


@Composable
fun StoreView( navController: NavController){
    val viewModel = viewModel<MissionViewModel>()
    val searchText by viewModel.searchText.collectAsState()
    val missions by viewModel.missions.collectAsState()
    val isSearching by viewModel.isSearching.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        if(isSearching) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }else {
            LazyColumn {
                items(missions) { missions ->
                    MissionView(missionDetailsItem = missions, navController)
                }
            }
        }
    }
}