package com.example.myprofile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myprofile.data.NoteRepository
import com.example.myprofile.data.NoteUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NoteViewModel : ViewModel() {

    private val repository = NoteRepository()

    private val _uiState = MutableStateFlow(NoteUiState(notes = repository.getAllNotes()))
    val uiState: StateFlow<NoteUiState> = _uiState.asStateFlow()

    fun refresh() {
        _uiState.update { it.copy(notes = repository.getAllNotes()) }
    }

    fun getNoteById(id: Int) = repository.getNoteById(id)

    fun addNote(title: String, content: String) {
        repository.addNote(title, content)
        refresh()
    }

    fun updateNote(id: Int, title: String, content: String) {
        repository.updateNote(id, title, content)
        refresh()
    }

    fun toggleFavorite(id: Int) {
        repository.toggleFavorite(id)
        refresh()
    }

    fun deleteNote(id: Int) {
        repository.deleteNote(id)
        refresh()
    }

    fun getFavorites() = repository.getFavorites()
}