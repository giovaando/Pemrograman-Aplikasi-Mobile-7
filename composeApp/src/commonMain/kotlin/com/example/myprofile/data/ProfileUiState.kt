package com.example.myprofile.data

/**
 * DATA CLASS — ProfileUiState
 * Menyimpan seluruh state UI aplikasi dalam satu data class.
 * Menggunakan copy() untuk update immutable state di ViewModel.
 */
data class ProfileUiState(
    // ── Data Profil ───────────────────────────────────────────
    val name: String = "Giovan Lado",
    val title: String = "Data Engineer | ITERA",
    val bio: String = "Mahasiswa Teknik Informatika Institut Teknologi Sumatera yang passionate dalam pengembangan aplikasi mobile cross-platform menggunakan Kotlin Multiplatform & Compose Multiplatform.",
    val email: String = "giovan.123140068@student.itera.ac.id",
    val phone: String = "+62 812-3456-7890",
    val location: String = "Bandar Lampung, Indonesia",
    val github: String = "github.com/02-068-GiovanLado",
    val skills: List<String> = listOf("Kotlin", "Compose", "Android", "KMP", "Coroutines", "MVVM", "Git"),

    // ── UI State ──────────────────────────────────────────────
    val isDarkMode: Boolean = false,        // Dark mode toggle
    val isEditMode: Boolean = false,        // Tampilkan/sembunyikan form edit
    val showContact: Boolean = true,        // Toggle kontak

    // ── Edit Form State (state hoisting dari TextField) ───────
    val editName: String = "",
    val editBio: String = "",

    // ── Feedback ──────────────────────────────────────────────
    val saveSuccess: Boolean = false        // Notifikasi simpan berhasil
)