package com.example.myprofile.data

import com.example.myprofile.db.NoteEntity

sealed class NotesUiState {
    object Loading  : NotesUiState()
    object Empty    : NotesUiState()
    data class Content(val notes: List<NoteEntity>) : NotesUiState()
    data class Error(val message: String)           : NotesUiState()
}