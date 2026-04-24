package com.example.myprofile.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.set

/**
 * REPOSITORY — ProfileRepository
 * Mengelola penyimpanan dan pembacaan data profil secara persisten
 * menggunakan multiplatform-settings (SharedPreferences di Android,
 * NSUserDefaults di iOS, Properties file di Desktop/JVM).
 *
 * Dipanggil dari ViewModel — View tidak pernah akses Repository langsung.
 */
class ProfileRepository {

    private val settings: Settings = Settings()

    // ── Key constants ─────────────────────────────────────────
    companion object {
        private const val KEY_NAME     = "profile_name"
        private const val KEY_TITLE    = "profile_title"
        private const val KEY_BIO      = "profile_bio"
        private const val KEY_EMAIL    = "profile_email"
        private const val KEY_PHONE    = "profile_phone"
        private const val KEY_LOCATION = "profile_location"
        private const val KEY_GITHUB   = "profile_github"
        private const val KEY_DARKMODE = "profile_darkmode"
    }

    // ── Default values ────────────────────────────────────────
    private val defaultName     = "Giovan Lado"
    private val defaultTitle    = "Data Engineer | ITERA"
    private val defaultBio      = "Mahasiswa Teknik Informatika Institut Teknologi Sumatera yang passionate dalam pengembangan aplikasi mobile cross-platform menggunakan Kotlin Multiplatform & Compose Multiplatform."
    private val defaultEmail    = "giovan.123140068@student.itera.ac.id"
    private val defaultPhone    = "+62 812-3456-7890"
    private val defaultLocation = "Bandar Lampung, Indonesia"
    private val defaultGithub   = "github.com/02-068-GiovanLado"

    // ── Load — baca semua data tersimpan ──────────────────────
    fun loadProfile(): ProfileUiState {
        return ProfileUiState(
            name     = settings.getString(KEY_NAME,     defaultName),
            title    = settings.getString(KEY_TITLE,    defaultTitle),
            bio      = settings.getString(KEY_BIO,      defaultBio),
            email    = settings.getString(KEY_EMAIL,    defaultEmail),
            phone    = settings.getString(KEY_PHONE,    defaultPhone),
            location = settings.getString(KEY_LOCATION, defaultLocation),
            github   = settings.getString(KEY_GITHUB,   defaultGithub),
            isDarkMode = settings.getBoolean(KEY_DARKMODE, false)
        )
    }

    // ── Save — simpan nama dan bio setelah edit ───────────────
    fun saveProfile(name: String, bio: String) {
        settings[KEY_NAME] = name
        settings[KEY_BIO]  = bio
    }

    // ── Save dark mode preference ─────────────────────────────
    fun saveDarkMode(isDark: Boolean) {
        settings[KEY_DARKMODE] = isDark
    }
}
