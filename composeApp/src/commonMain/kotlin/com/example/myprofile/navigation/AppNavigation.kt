package com.example.myprofile.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myprofile.data.NoteRepository
import com.example.myprofile.data.SettingsRepository
import com.example.myprofile.screens.*
import com.example.myprofile.viewmodel.NoteViewModel
import com.example.myprofile.viewmodel.ProfileViewModel
import com.example.myprofile.viewmodel.SettingsViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    noteRepository: NoteRepository,
    settingsRepository: SettingsRepository,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = viewModel { ProfileViewModel() }
) {
    val noteViewModel: NoteViewModel = viewModel(key = "noteVM") {
        NoteViewModel(noteRepository, settingsRepository)
    }
    val settingsViewModel: SettingsViewModel = viewModel(key = "settingsVM") {
        SettingsViewModel(settingsRepository)
    }

    NavHost(
        navController    = navController,
        startDestination = Screen.Notes.route,
        modifier         = modifier,
        // --- Implementasi Animasi Transisi ---
        enterTransition = {
            fadeIn(animationSpec = tween(300)) +
                    slideInHorizontally(initialOffsetX = { 100 }, animationSpec = tween(300))
        },
        exitTransition = {
            fadeOut(animationSpec = tween(300)) +
                    slideOutHorizontally(targetOffsetX = { -100 }, animationSpec = tween(300))
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(300)) +
                    slideInHorizontally(initialOffsetX = { -100 }, animationSpec = tween(300))
        },
        popExitTransition = {
            fadeOut(animationSpec = tween(300)) +
                    slideOutHorizontally(targetOffsetX = { 100 }, animationSpec = tween(300))
        }
    ) {
        composable(Screen.Notes.route) {
            NotesScreen(
                viewModel   = noteViewModel,
                onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) },
                onAddClick  = { navController.navigate(Screen.AddNote.route) }
            )
        }
        composable(Screen.Favorites.route) {
            FavoritesScreen(
                viewModel   = noteViewModel,
                onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(viewModel = profileViewModel)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(viewModel = settingsViewModel)
        }
        composable(
            route     = Screen.NoteDetail.route,
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { backStackEntry ->
            // ✅ PERBAIKAN: Menggunakan NavType.LongType.get() untuk KMP Kestabilan
            val args = backStackEntry.arguments
            val noteId = if (args != null) NavType.LongType.get(args, "noteId") else null

            if (noteId != null) {
                NoteDetailScreen(
                    noteId    = noteId,
                    viewModel = noteViewModel,
                    onBack    = { navController.popBackStack() },
                    onEdit    = { id -> navController.navigate(Screen.EditNote.createRoute(id)) }
                )
            }
        }
        composable(Screen.AddNote.route) {
            AddNoteScreen(viewModel = noteViewModel, onBack = { navController.popBackStack() })
        }
        composable(
            route     = Screen.EditNote.route,
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { backStackEntry ->
            // ✅ PERBAIKAN: Menggunakan NavType.LongType.get() untuk KMP Kestabilan
            val args = backStackEntry.arguments
            val noteId = if (args != null) NavType.LongType.get(args, "noteId") else null

            if (noteId != null) {
                EditNoteScreen(
                    noteId    = noteId,
                    viewModel = noteViewModel,
                    onBack    = { navController.popBackStack() }
                )
            }
        }
    }
}