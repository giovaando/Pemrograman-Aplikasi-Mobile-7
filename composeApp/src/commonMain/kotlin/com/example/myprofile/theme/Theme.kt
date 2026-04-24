package com.example.myprofile.theme

import androidx.compose.ui.graphics.Color

/**
 * THEME — AppColors
 * Warna terpusat untuk light dan dark mode.
 * ViewModel menyimpan isDarkMode, App() memilih color set yang tepat.
 */
object AppColors {

    // ── Light Mode ────────────────────────────────────────────
    val Primary       = Color(0xFF3949AB)
    val PrimaryDark   = Color(0xFF1A237E)
    val Secondary     = Color(0xFF7986CB)

    val BackgroundLight  = Color(0xFFF5F5F5)
    val SurfaceLight     = Color.White
    val DividerLight     = Color(0xFFE8EAF6)
    val TextPrimaryLight = Color(0xFF212121)
    val TextSecondaryLight = Color(0xFF424242)
    val TextHintLight    = Color(0xFF9E9E9E)

    // ── Dark Mode ─────────────────────────────────────────────
    val BackgroundDark   = Color(0xFF121212)
    val SurfaceDark      = Color(0xFF1E1E1E)
    val DividerDark      = Color(0xFF2C2C2C)
    val TextPrimaryDark  = Color(0xFFE0E0E0)
    val TextSecondaryDark = Color(0xFFBDBDBD)
    val TextHintDark     = Color(0xFF757575)

    // ── Icon tints (sama di kedua mode) ───────────────────────
    val IconEmail    = Color(0xFFE53935)
    val IconPhone    = Color(0xFF43A047)
    val IconLocation = Color(0xFFFB8C00)
    val IconGithub   = Color(0xFF9E9E9E)
    val IconDefault  = Color(0xFF3949AB)
}

/** Helper: ambil warna sesuai mode */
data class AppTheme(val isDark: Boolean) {
    val background   get() = if (isDark) AppColors.BackgroundDark   else AppColors.BackgroundLight
    val surface      get() = if (isDark) AppColors.SurfaceDark      else AppColors.SurfaceLight
    val divider      get() = if (isDark) AppColors.DividerDark      else AppColors.DividerLight
    val textPrimary  get() = if (isDark) AppColors.TextPrimaryDark  else AppColors.TextPrimaryLight
    val textSecondary get() = if (isDark) AppColors.TextSecondaryDark else AppColors.TextSecondaryLight
    val textHint     get() = if (isDark) AppColors.TextHintDark     else AppColors.TextHintLight
}