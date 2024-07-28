package com.example.spacex

import androidx.annotation.DrawableRes

sealed class Screens ( val title : String , val route : String ) {



    sealed class BottomScreen(
        val bTitle: String, val bRoute: String, @DrawableRes val icon: Int
    ):Screens(bTitle,bRoute){
        object Home : BottomScreen("Home", "home", R.drawable.baseline_home_24)

        object Store : BottomScreen(
            "Store", "store", R.drawable.baseline_web_24
        )
        object Search: BottomScreen(
            "Search", "search",
            R.drawable.baseline_manage_search_24
        )
    }
    object Detail : Screens("Home" ,"detail_screen")
}

val screensInBottom = listOf(
    Screens.BottomScreen.Home,
    Screens.BottomScreen.Store,
    Screens.BottomScreen.Search
)