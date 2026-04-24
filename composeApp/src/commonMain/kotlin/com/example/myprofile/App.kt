package com.example.myprofile

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myprofile.components.BottomNavBar
import com.example.myprofile.navigation.AppNavigation
import com.example.myprofile.navigation.Screen

private val bottomNavRoutes = setOf(
    Screen.Notes.route,
    Screen.Favorites.route,
    Screen.Profile.route
)

@Composable
fun App() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    MaterialTheme {
        Scaffold(
            bottomBar = {
                if (currentRoute in bottomNavRoutes) {
                    BottomNavBar(navController = navController)
                }
            }
        ) { padding ->
            AppNavigation(
                navController = navController,
                modifier = Modifier.padding(padding)
            )
        }
    }
}