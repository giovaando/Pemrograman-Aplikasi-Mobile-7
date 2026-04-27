package com.example.myprofile

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myprofile.components.BottomNavBar
import com.example.myprofile.data.NoteRepository
import com.example.myprofile.data.SettingsRepository
import com.example.myprofile.database.DatabaseDriverFactory
import com.example.myprofile.db.NotesDatabase
import com.example.myprofile.navigation.AppNavigation
import com.example.myprofile.navigation.Screen
import com.example.myprofile.theme.DarkColorScheme
import com.example.myprofile.theme.LightColorScheme
import com.russhwolf.settings.Settings

private val bottomNavRoutes = setOf(
    Screen.Notes.route,
    Screen.Favorites.route,
    Screen.Profile.route,
    Screen.Settings.route
)

@Composable
fun App(driverFactory: DatabaseDriverFactory) {
    val database     = remember { NotesDatabase(driverFactory.createDriver()) }
    val noteRepo     = remember { NoteRepository(database) }
    val settings     = remember { Settings() }
    val settingsRepo = remember { SettingsRepository(settings) }

    val currentTheme by settingsRepo.themeFlow.collectAsState()
    val systemIsDark  = isSystemInDarkTheme()

    val useDarkTheme = when (currentTheme) {
        SettingsRepository.THEME_DARK  -> true
        SettingsRepository.THEME_LIGHT -> false
        else                           -> systemIsDark
    }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    MaterialTheme(
        colorScheme = if (useDarkTheme) DarkColorScheme else LightColorScheme
    ) {
        Scaffold(
            bottomBar = {
                if (currentRoute in bottomNavRoutes) {
                    BottomNavBar(navController = navController)
                }
            }
        ) { padding ->
            AppNavigation(
                navController      = navController,
                noteRepository     = noteRepo,
                settingsRepository = settingsRepo,
                modifier           = Modifier.padding(padding)
            )
        }
    }
}