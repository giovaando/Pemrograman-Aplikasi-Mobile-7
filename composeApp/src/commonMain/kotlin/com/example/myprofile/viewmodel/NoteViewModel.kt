package com.example.myprofile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofile.data.NoteRepository
import com.example.myprofile.data.NotesUiState
import com.example.myprofile.db.NoteEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    // Search query state
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Notes UI state — reacts to search query changes
    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<NotesUiState> = _searchQuery
        .flatMapLatest { query ->
            if (query.isBlank()) repository.getAllNotes()
            else repository.searchNotes(query)
        }
        .map { notes ->
            if (notes.isEmpty()) NotesUiState.Empty
            else NotesUiState.Content(notes)
        }
        .onStart { emit(NotesUiState.Loading) }
        .catch { e -> emit(NotesUiState.Error(e.message ?: "Unknown error")) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NotesUiState.Loading
        )

    // Favorites state
    val favoritesState: StateFlow<NotesUiState> = repository.getFavorites()
        .map { notes ->
            if (notes.isEmpty()) NotesUiState.Empty
            else NotesUiState.Content(notes)
        }
        .onStart { emit(NotesUiState.Loading) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = NotesUiState.Loading
        )

    // Detail note state
    private val _selectedNoteId = MutableStateFlow<Long?>(null)
    val selectedNote: StateFlow<NoteEntity?> = _selectedNoteId
        .flatMapLatest { id ->
            if (id == null) flowOf(null)
            else repository.getNoteById(id)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun selectNote(id: Long) {
        _selectedNoteId.value = id
    }

    fun addNote(title: String, content: String) {
        viewModelScope.launch { repository.insertNote(title, content) }
    }

    fun updateNote(id: Long, title: String, content: String) {
        viewModelScope.launch { repository.updateNote(id, title, content) }
    }

    fun toggleFavorite(id: Long) {
        viewModelScope.launch { repository.toggleFavorite(id) }
    }

    fun deleteNote(id: Long) {
        viewModelScope.launch { repository.deleteNote(id) }
    }
}