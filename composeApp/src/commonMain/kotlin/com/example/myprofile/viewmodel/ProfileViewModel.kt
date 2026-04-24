package com.example.myprofile.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myprofile.data.ProfileRepository
import com.example.myprofile.data.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * VIEWMODEL — ProfileViewModel (updated)
 * Sekarang terhubung ke ProfileRepository untuk menyimpan dan
 * memuat data secara persisten. Data tidak hilang saat app ditutup.
 *
 * Alur data:
 *   App dibuka → ViewModel init → Repository.loadProfile() → tampil di UI
 *   User simpan → ViewModel.saveProfile() → Repository.saveProfile() → tersimpan
 */
class ProfileViewModel : ViewModel() {

    private val repository = ProfileRepository()

    // ── State diinisialisasi dari data tersimpan ──────────────
    private val _uiState = MutableStateFlow(repository.loadProfile())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    // ══════════════════════════════════════════════════════════
    // DARK MODE — simpan ke storage agar persist
    // ══════════════════════════════════════════════════════════

    fun toggleDarkMode() {
        val newValue = !_uiState.value.isDarkMode
        _uiState.update { it.copy(isDarkMode = newValue) }
        repository.saveDarkMode(newValue)
    }

    // ══════════════════════════════════════════════════════════
    // KONTAK TOGGLE
    // ══════════════════════════════════════════════════════════

    fun toggleContact() {
        _uiState.update { it.copy(showContact = !it.showContact) }
    }

    // ══════════════════════════════════════════════════════════
    // EDIT PROFILE
    // ══════════════════════════════════════════════════════════

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

    /** Simpan ke ViewModel state DAN ke persistent storage */
    fun saveProfile() {
        val current = _uiState.value
        val newName = current.editName.ifBlank { current.name }
        val newBio  = current.editBio.ifBlank { current.bio }

        // 1. Update state di ViewModel
        _uiState.update {
            it.copy(
                name        = newName,
                bio         = newBio,
                isEditMode  = false,
                saveSuccess = true
            )
        }

        // 2. Simpan ke storage (persists setelah app ditutup)
        repository.saveProfile(name = newName, bio = newBio)
    }
}