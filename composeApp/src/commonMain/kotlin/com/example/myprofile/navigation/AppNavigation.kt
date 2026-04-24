package com.example.myprofile.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.myprofile.screens.AddNoteScreen
import com.example.myprofile.screens.EditNoteScreen
import com.example.myprofile.screens.FavoritesScreen
import com.example.myprofile.screens.NoteDetailScreen
import com.example.myprofile.screens.NotesScreen
import com.example.myprofile.screens.ProfileScreen
import com.example.myprofile.viewmodel.NoteViewModel
import com.example.myprofile.viewmodel.ProfileViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    noteViewModel: NoteViewModel = viewModel { NoteViewModel() },
    profileViewModel: ProfileViewModel = viewModel { ProfileViewModel() }
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Notes.route,
        modifier = modifier
    ) {
        composable(Screen.Notes.route) {
            NotesScreen(
                viewModel = noteViewModel,
                onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) },
                onAddClick = { navController.navigate(Screen.AddNote.route) }
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                viewModel = noteViewModel,
                onNoteClick = { id -> navController.navigate(Screen.NoteDetail.createRoute(id)) }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(viewModel = profileViewModel)
        }

        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: return@composable
            NoteDetailScreen(
                noteId = noteId,
                viewModel = noteViewModel,
                onBack = { navController.popBackStack() },
                onEdit = { id -> navController.navigate(Screen.EditNote.createRoute(id)) }
            )
        }

        composable(Screen.AddNote.route) {
            AddNoteScreen(
                viewModel = noteViewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditNote.route,
            arguments = listOf(navArgument("noteId") { type = NavType.IntType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getInt("noteId") ?: return@composable
            EditNoteScreen(
                noteId = noteId,
                viewModel = noteViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}