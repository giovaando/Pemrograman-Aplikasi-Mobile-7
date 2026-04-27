package com.example.myprofile.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ── Brand Colors (Clean / Stitch-style) ───────────────────────
object AppColors {
    // Primary — indigo/violet seperti stitch
    val Primary        = Color(0xFF4F46E5)   // indigo-600
    val PrimaryLight   = Color(0xFFE0E7FF)   // indigo-100
    val PrimaryDark    = Color(0xFF3730A3)   // indigo-800
    val Secondary      = Color(0xFF818CF8)   // indigo-400

    // Card — semua putih/abu, tidak berwarna-warni
    val CardDefault    = Color(0xFFFFFFFF)
    val CardBorder     = Color(0xFFE5E7EB)

    // Tag text (tidak dipakai untuk warna kartu, hanya label)
    val TagYellowText  = Color(0xFF92400E)
    val TagMintText    = Color(0xFF065F46)
    val TagPurpleText  = Color(0xFF3730A3)
    val TagPinkText    = Color(0xFF9D174D)
    val TagBlueText    = Color(0xFF1E40AF)

    // Surface & background — sangat bersih
    val Background     = Color(0xFFF2F2F7)   // iOS-like light grey
    val Surface        = Color(0xFFFFFFFF)
    val SurfaceVariant = Color(0xFFF2F2F7)
    val Border         = Color(0xFFE5E7EB)
    val BorderMed      = Color(0xFFD1D5DB)

    // Text
    val TextPrimary    = Color(0xFF111827)
    val TextSecondary  = Color(0xFF6B7280)
    val TextHint       = Color(0xFF9CA3AF)
    val TextDate       = Color(0xFF9CA3AF)

    // Icon tints
    val IconEmail      = Color(0xFFEF4444)
    val IconPhone      = Color(0xFF10B981)
    val IconLocation   = Color(0xFFF59E0B)
    val IconGithub     = Color(0xFF6B7280)
    val IconDefault    = Color(0xFF4F46E5)

    // Nav — stitch style: ikon aktif berwarna primary, teks di bawah
    val NavActive      = Color(0xFF4F46E5)
    val NavInactive    = Color(0xFF9CA3AF)

    // Untuk kompatibilitas dengan kode lama (CardYellow dipakai di ProfileScreen)
    val CardYellow     = Color(0xFFEEF2FF)   // indigo-50, netral
    val CardMint       = Color(0xFFF0FDF4)
    val CardPurple     = Color(0xFFEEF2FF)
    val CardPink       = Color(0xFFFFF1F2)
    val CardBlue       = Color(0xFFEFF6FF)
}

// ── Card color cycling — putih semua, border abu ───────────────
// Stitch menggunakan kartu putih seragam, bukan warna-warni
val cardColors = listOf(
    AppColors.CardDefault to AppColors.Primary,
    AppColors.CardDefault to AppColors.Primary,
    AppColors.CardDefault to AppColors.Primary,
    AppColors.CardDefault to AppColors.Primary,
    AppColors.CardDefault to AppColors.Primary,
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
    primary            = AppColors.Secondary,
    onPrimary          = Color.White,
    primaryContainer   = Color(0xFF312E81),
    onPrimaryContainer = Color(0xFFC7D2FE),
    background         = Color(0xFF0F172A),
    onBackground       = Color(0xFFF1F5F9),
    surface            = Color(0xFF1E293B),
    onSurface          = Color(0xFFF1F5F9),
    surfaceVariant     = Color(0xFF334155),
    onSurfaceVariant   = Color(0xFF94A3B8),
    outline            = Color(0xFF475569),
)