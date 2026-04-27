package com.example.myprofile.data

import com.russhwolf.settings.Settings
import com.russhwolf.settings.get
import com.russhwolf.settings.set
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsRepository(private val settings: Settings) {

    companion object {
        const val KEY_THEME      = "app_theme"
        const val KEY_SORT_ORDER = "sort_order"

        const val THEME_SYSTEM = "system"
        const val THEME_LIGHT  = "light"
        const val THEME_DARK   = "dark"

        const val SORT_NEWEST = "newest"
        const val SORT_OLDEST = "oldest"
        const val SORT_TITLE  = "title"
    }

    // ── Internal mutable state ─────────────────────────────────
    private val _theme = MutableStateFlow(settings[KEY_THEME, THEME_SYSTEM])
    private val _sortOrder = MutableStateFlow(settings[KEY_SORT_ORDER, SORT_NEWEST])

    // ── Public read-only flows ─────────────────────────────────
    val themeFlow: StateFlow<String> = _theme.asStateFlow()
    val sortOrderFlow: StateFlow<String> = _sortOrder.asStateFlow()

    // ── Getters (untuk inisialisasi ViewModel) ─────────────────
    var theme: String
        get() = settings[KEY_THEME, THEME_SYSTEM]
        set(value) {
            settings[KEY_THEME] = value
            _theme.value = value
        }

    var sortOrder: String
        get() = settings[KEY_SORT_ORDER, SORT_NEWEST]
        set(value) {
            settings[KEY_SORT_ORDER] = value
            _sortOrder.value = value
        }
}