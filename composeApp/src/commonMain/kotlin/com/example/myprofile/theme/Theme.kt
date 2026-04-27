package com.example.myprofile.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ── Brand Colors ───────────────────────────────────────────────
object AppColors {
    // Primary
    val Primary        = Color(0xFF7C3AED)   // violet-600
    val PrimaryLight   = Color(0xFFDDD6FE)   // violet-200
    val PrimaryDark    = Color(0xFF5B21B6)   // violet-700
    val Secondary      = Color(0xFF9F67F5)   // violet-400 (avatar placeholder bg)

    // Card accent fills
    val CardYellow     = Color(0xFFFDE68A)   // amber-200
    val CardMint       = Color(0xFFA7F3D0)   // emerald-200
    val CardPurple     = Color(0xFFDDD6FE)   // violet-200
    val CardPink       = Color(0xFFFBCFE8)   // pink-200
    val CardBlue       = Color(0xFFBFDBFE)   // blue-200

    // Tag text (darkened version of card fills)
    val TagYellowText  = Color(0xFF92400E)
    val TagMintText    = Color(0xFF065F46)
    val TagPurpleText  = Color(0xFF5B21B6)
    val TagPinkText    = Color(0xFF9D174D)
    val TagBlueText    = Color(0xFF1E40AF)

    // Surface & background
    val Background     = Color(0xFFFAFAF8)
    val Surface        = Color(0xFFFFFFFF)
    val SurfaceVariant = Color(0xFFF3F4F6)
    val Border         = Color(0xFFF3F4F6)
    val BorderMed      = Color(0xFFE5E7EB)

    // Text
    val TextPrimary    = Color(0xFF111827)
    val TextSecondary  = Color(0xFF6B7280)
    val TextHint       = Color(0xFF9CA3AF)

    // Icon tints
    val IconEmail      = Color(0xFFEF4444)
    val IconPhone      = Color(0xFF10B981)
    val IconLocation   = Color(0xFFF59E0B)
    val IconGithub     = Color(0xFF6B7280)
    val IconDefault    = Color(0xFF7C3AED)

    // Nav
    val NavActive      = Color(0xFF111827)
    val NavInactive    = Color(0xFFE5E7EB)
}

// ── Card color cycling ─────────────────────────────────────────
val cardColors = listOf(
    AppColors.CardYellow to AppColors.TagYellowText,
    AppColors.CardMint   to AppColors.TagMintText,
    AppColors.CardPurple to AppColors.TagPurpleText,
    AppColors.CardPink   to AppColors.TagPinkText,
    AppColors.CardBlue   to AppColors.TagBlueText,
)

fun cardColorForIndex(index: Int) = cardColors[index % cardColors.size]

// ── Material3 Color Schemes ────────────────────────────────────
val LightColorScheme = lightColorScheme(
    primary            = AppColors.Primary,
    onPrimary          = Color.White,
    primaryContainer   = AppColors.PrimaryLight,
    onPrimaryContainer = AppColors.PrimaryDark,
    background         = AppColors.Background,
    onBackground       = AppColors.TextPrimary,
    surface            = AppColors.Surface,
    onSurface          = AppColors.TextPrimary,
    surfaceVariant     = AppColors.SurfaceVariant,
    onSurfaceVariant   = AppColors.TextSecondary,
    outline            = AppColors.BorderMed,
)

val DarkColorScheme = darkColorScheme(
    primary            = AppColors.PrimaryLight,
    onPrimary          = AppColors.PrimaryDark,
    primaryContainer   = AppColors.PrimaryDark,
    onPrimaryContainer = AppColors.PrimaryLight,
    background         = Color(0xFF0F172A),
    onBackground       = Color(0xFFF1F5F9),
    surface            = Color(0xFF1E293B),
    onSurface          = Color(0xFFF1F5F9),
    surfaceVariant     = Color(0xFF334155),
    onSurfaceVariant   = Color(0xFF94A3B8),
    outline            = Color(0xFF475569),
)