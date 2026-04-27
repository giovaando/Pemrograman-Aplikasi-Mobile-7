package com.example.myprofile

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myprofile.components.BottomNavBar
import com.example.myprofile.data.NoteRepository
import com.example.myprofile.data.SettingsRepository
import com.example.myprofile.database.DatabaseDriverFactory
import com.example.myprofile.db.NotesDatabase
import com.example.myprofile.navigation.AppNavigation
import com.example.myprofile.navigation.Screen
import com.example.myprofile.theme.AppColors
import com.russhwolf.settings.Settings
import androidx.compose.material3.Scaffold

private val bottomNavRoutes = setOf(
    Screen.Notes.route,
    Screen.Favorites.route,
    Screen.Profile.route,
    Screen.Settings.route
)

// ── Dark color scheme matching reference ──────────────────────────────────
private val RefDarkColors = darkColorScheme(
    primary            = AppColors.AccentYellow,
    onPrimary          = Color(0xFF1A1200),
    secondary          = AppColors.AccentGreen,
    onSecondary        = Color(0xFF001A08),
    tertiary           = AppColors.AccentPurple,
    onTertiary         = Color(0xFF0D0020),
    background         = AppColors.BackgroundDark,
    onBackground       = Color(0xFFF0F0FF),
    surface            = AppColors.SurfaceDark,
    onSurface          = Color(0xFFD0D0E8),
    surfaceVariant     = AppColors.SurfaceCardDark,
    onSurfaceVariant   = Color(0xFFB0B0CC),
    outline            = Color(0xFF3A3A52),
    error              = Color(0xFFFF6B8A)
)

// ── Light color scheme ─────────────────────────────────────────────────────
private val RefLightColors = lightColorScheme(
    primary            = Color(0xFF5C4EEB),
    onPrimary          = Color.White,
    secondary          = Color(0xFF2DBD7E),
    onSecondary        = Color.White,
    tertiary           = Color(0xFF9B7EFF),
    onTertiary         = Color.White,
    background         = AppColors.BackgroundLight,
    onBackground       = AppColors.TextPrimaryLight,
    surface            = AppColors.SurfaceLight,
    onSurface          = AppColors.TextPrimaryLight,
    surfaceVariant     = Color(0xFFF0F0FA),
    onSurfaceVariant   = AppColors.TextSecondaryLight,
    outline            = Color(0xFFDDDDEE),
    error              = Color(0xFFE53935)
)

@Composable
fun App(driverFactory: DatabaseDriverFactory) {
    val database     = remember { NotesDatabase(driverFactory.createDriver()) }
    val noteRepo     = remember { NoteRepository(database) }
    val settings     = remember { Settings() }
    val settingsRepo = remember { SettingsRepository(settings) }

    val currentTheme by settingsRepo.themeFlow.collectAsState()
    val systemIsDark = isSystemInDarkTheme()

    val useDarkTheme = when (currentTheme) {
        SettingsRepository.THEME_DARK  -> true
        SettingsRepository.THEME_LIGHT -> false
        else                           -> systemIsDark
    }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    MaterialTheme(
        colorScheme = if (useDarkTheme) RefDarkColors else RefLightColors
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
