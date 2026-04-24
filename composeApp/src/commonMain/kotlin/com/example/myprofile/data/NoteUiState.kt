package com.example.myprofile.data

import com.example.myprofile.model.Note

data class NoteUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val saveSuccess: Boolean = false
)