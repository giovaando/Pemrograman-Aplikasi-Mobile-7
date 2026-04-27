package com.example.myprofile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myprofile.data.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class SettingsUiState(
    val theme: String     = SettingsRepository.THEME_SYSTEM,
    val sortOrder: String = SettingsRepository.SORT_NEWEST
)

class SettingsViewModel(val repo: SettingsRepository) : ViewModel() {

    // ── Langsung pakai flow dari repository ───────────────────
    // Ini memastikan semua observer (App, NoteViewModel, dll)
    // membaca dari sumber yang sama.
    val themeFlow: StateFlow<String> = repo.themeFlow
    val sortOrderFlow: StateFlow<String> = repo.sortOrderFlow

    // ── UiState untuk SettingsScreen ──────────────────────────
    val uiState: StateFlow<SettingsUiState> = combine(
        themeFlow,
        sortOrderFlow
    ) { theme, sort ->
        SettingsUiState(theme = theme, sortOrder = sort)
    }.stateIn(
        scope        = viewModelScope,
        started      = SharingStarted.WhileSubscribed(5_000),
        initialValue = SettingsUiState(
            theme     = repo.theme,
            sortOrder = repo.sortOrder
        )
    )

    fun setTheme(theme: String) {
        repo.theme = theme          // update settings + emit ke flow
    }

    fun setSortOrder(sortOrder: String) {
        repo.sortOrder = sortOrder  // update settings + emit ke flow
    }
}