package com.example.myprofile.navigation

sealed class Screen(val route: String) {
    object Notes     : Screen("notes")
    object Favorites : Screen("favorites")
    object Profile   : Screen("profile")
    object Settings  : Screen("settings")   // ← BARU

    object NoteDetail : Screen("note_detail/{noteId}") {
        fun createRoute(noteId: Long) = "note_detail/$noteId"
    }
    object AddNote : Screen("add_note")
    object EditNote : Screen("edit_note/{noteId}") {
        fun createRoute(noteId: Long) = "edit_note/$noteId"
    }
}