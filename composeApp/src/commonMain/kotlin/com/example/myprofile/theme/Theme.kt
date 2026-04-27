package com.example.myprofile.theme

import androidx.compose.ui.graphics.Color

/**
 * THEME — AppColors
 * Redesign: Dark-first with vibrant accent cards (yellow, green, purple)
 * inspired by the reference UI.
 */
object AppColors {

    // ── Brand Accents ─────────────────────────────────────────
    val AccentYellow     = Color(0xFFFFE566)
    val AccentGreen      = Color(0xFF9FFFB0)
    val AccentPurple     = Color(0xFFBBA8FF)
    val AccentPink       = Color(0xFFFFADC9)
    val AccentOrange     = Color(0xFFFFB86C)

    // ── Primary (for buttons, FAB, etc.) ──────────────────────
    val Primary       = Color(0xFFFFE566)   // yellow accent
    val PrimaryDark   = Color(0xFFD4BC00)
    val Secondary     = Color(0xFF9FFFB0)

    // ── Light Mode ────────────────────────────────────────────
    val BackgroundLight    = Color(0xFFF4F4F8)
    val SurfaceLight       = Color(0xFFFFFFFF)
    val DividerLight       = Color(0xFFE0E0E0)
    val TextPrimaryLight   = Color(0xFF1A1A2E)
    val TextSecondaryLight = Color(0xFF4A4A6A)
    val TextHintLight      = Color(0xFF9E9EBE)

    // ── Dark Mode ─────────────────────────────────────────────
    val BackgroundDark    = Color(0xFF111118)
    val SurfaceDark       = Color(0xFF1C1C27)
    val SurfaceCardDark   = Color(0xFF242433)
    val DividerDark       = Color(0xFF2E2E42)
    val TextPrimaryDark   = Color(0xFFF0F0FF)
    val TextSecondaryDark = Color(0xFFB0B0CC)
    val TextHintDark      = Color(0xFF6060808)

    // ── Note card accent colors ────────────────────────────────
    val NoteYellow  = Color(0xFFFFE566)
    val NoteGreen   = Color(0xFF9FFFB0)
    val NotePurple  = Color(0xFFBBA8FF)
    val NotePink    = Color(0xFFFFADC9)

    // ── Icon tints ────────────────────────────────────────────
    val IconEmail    = Color(0xFFFF6B8A)
    val IconPhone    = Color(0xFF9FFFB0)
    val IconLocation = Color(0xFFFFB86C)
    val IconGithub   = Color(0xFFBBA8FF)
    val IconDefault  = Color(0xFFFFE566)
}

/** Helper: ambil warna sesuai mode */
data class AppTheme(val isDark: Boolean) {
    val background    get() = if (isDark) AppColors.BackgroundDark   else AppColors.BackgroundLight
    val surface       get() = if (isDark) AppColors.SurfaceDark      else AppColors.SurfaceLight
    val divider       get() = if (isDark) AppColors.DividerDark      else AppColors.DividerLight
    val textPrimary   get() = if (isDark) AppColors.TextPrimaryDark  else AppColors.TextPrimaryLight
    val textSecondary get() = if (isDark) AppColors.TextSecondaryDark else AppColors.TextSecondaryLight
    val textHint      get() = if (isDark) AppColors.TextHintDark     else AppColors.TextHintLight
}
