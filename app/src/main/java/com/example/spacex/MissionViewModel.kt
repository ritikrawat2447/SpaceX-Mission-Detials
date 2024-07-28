package com.example.spacex

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spacex.Data.MissionDetailsItem
import com.example.spacex.Data.missionService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class MissionViewModel() : ViewModel() {

    private val _missionState = mutableStateOf(MissionState())
    private val _currentScreen: MutableState<Screens> = mutableStateOf(Screens.BottomScreen.Home)

    //first state whether the search is happening or not
    private val _isSearching = MutableStateFlow(false)
    //second state the text typed by the user
    private val _searchText = MutableStateFlow("")
    private val _missions = MutableStateFlow(listOf<MissionDetailsItem>())


    val currentScreen: MutableState<Screens> get() = _currentScreen
    val missionState : State<MissionState> = _missionState

    val isSearching = _isSearching.asStateFlow()
    val searchText = _searchText.asStateFlow()

    val missions = searchText
        .combine(_missions){ text , mission ->
            if ( text.isBlank() ){
                mission
            }else{
                mission.filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _missions.value
        )
    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

    init {
        fetchMissionData()
    }

    private fun fetchMissionData(){
        viewModelScope.launch {
            try{
                val response = missionService.getMissionDetails()
                _missionState.value = _missionState.value.copy(
                    loading = false,
                    list = response,
                    error = null
                )
            }catch(e : Exception){
                _missionState.value = _missionState.value.copy(
                    loading = false,
                    error = "Error fetching Categories ${e.message}"
                )
            }
        }
    }

    data class MissionState(
        val loading: Boolean = true,
        val list: List<MissionDetailsItem> = emptyList(),
        val error: String? = null
    )

}