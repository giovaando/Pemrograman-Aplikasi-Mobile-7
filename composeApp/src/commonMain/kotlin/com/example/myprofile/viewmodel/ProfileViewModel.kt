package com.example.myprofile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myprofile.data.ProfileRepository
import com.example.myprofile.data.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * VIEWMODEL — ProfileViewModel
 * Dark mode dihapus dari sini — sekarang dikelola terpusat
 * oleh SettingsRepository dan SettingsViewModel.
 */
class ProfileViewModel : ViewModel() {

    private val repository = ProfileRepository()

    private val _uiState = MutableStateFlow(repository.loadProfile())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    // ── Kontak Toggle ─────────────────────────────────────────
    fun toggleContact() {
        _uiState.update { it.copy(showContact = !it.showContact) }
    }

    // ── Edit Profile ──────────────────────────────────────────
    fun openEditMode() {
        _uiState.update {
            it.copy(
                isEditMode  = true,
                editName    = it.name,
                editBio     = it.bio,
                saveSuccess = false
            )
        }
    }

    fun closeEditMode() {
        _uiState.update { it.copy(isEditMode = false, saveSuccess = false) }
    }

    fun onEditNameChange(value: String) {
        _uiState.update { it.copy(editName = value) }
    }

    fun onEditBioChange(value: String) {
        _uiState.update { it.copy(editBio = value) }
    }

    fun saveProfile() {
        val current = _uiState.value
        val newName = current.editName.ifBlank { current.name }
        val newBio  = current.editBio.ifBlank { current.bio }

        _uiState.update {
            it.copy(
                name        = newName,
                bio         = newBio,
                isEditMode  = false,
                saveSuccess = true
            )
        }
        repository.saveProfile(name = newName, bio = newBio)
    }
}