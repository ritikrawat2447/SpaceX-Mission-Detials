package com.example.spacex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.spacex.AllScreens.DetailScreen
import com.example.spacex.AllScreens.HomeScreen
import com.example.spacex.AllScreens.SearchScreen
import com.example.spacex.AllScreens.StoreScreen
import com.example.spacex.ui.theme.SpaceXTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            SpaceXTheme {
                Column(modifier = Modifier.fillMaxSize()) {
                    MainScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val viewModel: MissionViewModel = viewModel()
    val currentScreen = remember {
        viewModel.currentScreen.value
    }
    val title = remember {
        mutableStateOf(currentScreen.title)
    }
    val controller: NavController = rememberNavController()
    val navBackStackEntry by controller.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = { TopAppBar(title = { Text(text = title.value) }) },
        bottomBar = {
            NavigationBar {
                screensInBottom.forEach { item ->
                    val isSelected = currentRoute == item.bRoute
                    val tint = if (isSelected) Color.White else Color.Black
                    NavigationBarItem(selected = currentRoute == item.bRoute,
                        onClick = {
                            controller.navigate(item.bRoute)
                            title.value = item.bTitle
                        },
                        icon = {

                            Icon(
                                tint = tint,
                                contentDescription = item.bTitle,
                                painter = painterResource(id = item.icon)
                            )
                        },
                        label = { Text(text = item.bTitle, color = tint) }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            navigation(navController = controller )
        }
    }
}

@Composable
fun navigation(navController: NavController ) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = Screens.BottomScreen.Home.bRoute
    ) {
        composable(Screens.BottomScreen.Home.bRoute) {
            HomeScreen( navController )
        }
        composable(Screens.BottomScreen.Store.bRoute) {
            StoreScreen()
        }
        composable(Screens.BottomScreen.Search.bRoute) {
            SearchScreen(navController)
        }
        composable(
            route = Screens.Detail.route + "/{itemJson}",
            arguments = listOf(navArgument("itemJson") { type = NavType.IntType })
        ) { backStackEntry ->
            val itemJson = backStackEntry.arguments?.getInt("itemJson")
            DetailScreen(itemJson)
        }

    }
}