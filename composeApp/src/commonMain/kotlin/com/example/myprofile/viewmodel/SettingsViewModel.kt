package com.example.myprofile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myprofile.data.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SettingsUiState(
    val theme: String     = SettingsRepository.THEME_SYSTEM,
    val sortOrder: String = SettingsRepository.SORT_NEWEST
)

class SettingsViewModel(private val repo: SettingsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SettingsUiState(theme = repo.theme, sortOrder = repo.sortOrder)
    )
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun setTheme(theme: String) {
        repo.theme = theme
        _uiState.update { it.copy(theme = theme) }
    }

    fun setSortOrder(sortOrder: String) {
        repo.sortOrder = sortOrder
        _uiState.update { it.copy(sortOrder = sortOrder) }
    }
}