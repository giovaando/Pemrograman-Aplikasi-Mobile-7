package com.example.myprofile.data

data class ProfileUiState(
    // ── Data Profil ───────────────────────────────────────────
    val name: String     = "Giovan Lado",
    val title: String    = "Data Engineer | ITERA",
    val bio: String      = "Mahasiswa Teknik Informatika Institut Teknologi Sumatera yang passionate dalam pengembangan aplikasi mobile cross-platform menggunakan Kotlin Multiplatform & Compose Multiplatform.",
    val email: String    = "giovan.123140068@student.itera.ac.id",
    val phone: String    = "+62 812-3456-7890",
    val location: String = "Bandar Lampung, Indonesia",
    val github: String   = "github.com/02-068-GiovanLado",
    val skills: List<String> = listOf("Kotlin", "Compose", "Android", "KMP", "Coroutines", "MVVM", "Git"),

    // ── UI State ──────────────────────────────────────────────
    val isEditMode: Boolean  = false,
    val showContact: Boolean = true,

    // ── Edit Form State ───────────────────────────────────────
    val editName: String = "",
    val editBio: String  = "",

    // ── Feedback ──────────────────────────────────────────────
    val saveSuccess: Boolean = false
)