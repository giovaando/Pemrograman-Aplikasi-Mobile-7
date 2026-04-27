package com.example.myprofile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.data.SettingsRepository
import com.example.myprofile.theme.AppColors
import com.example.myprofile.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // ── Header ────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 16.dp)
            ) {
                Text("Atur", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("Pengaturan", fontSize = 26.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
            }

            Column(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // ── Tema ──────────────────────────────────────
                SettingsCard(
                    badge      = "Tema",
                    badgeBg    = AppColors.PrimaryLight,
                    badgeText  = AppColors.PrimaryDark,
                    title      = "Tampilan Aplikasi",
                    icon       = Icons.Filled.Star,
                    iconTint   = AppColors.Primary
                ) {
                    val themes = listOf(
                        SettingsRepository.THEME_SYSTEM to "Ikuti Sistem",
                        SettingsRepository.THEME_LIGHT  to "Terang",
                        SettingsRepository.THEME_DARK   to "Gelap"
                    )
                    themes.forEachIndexed { i, (value, label) ->
                        if (i > 0) HorizontalDivider(color = MaterialTheme.colorScheme.background, thickness = 1.dp)
                        SettingsOption(
                            label    = label,
                            selected = uiState.theme == value,
                            onClick  = { viewModel.setTheme(value) }
                        )
                    }
                }

                // ── Urutan ────────────────────────────────────
                SettingsCard(
                    badge     = "Urutan",
                    badgeBg   = AppColors.CardMint,
                    badgeText = AppColors.TagMintText,
                    title     = "Tampilkan Catatan",
                    icon      = Icons.Filled.List,
                    iconTint  = Color(0xFF10B981)
                ) {
                    val sorts = listOf(
                        SettingsRepository.SORT_NEWEST to "Terbaru dulu",
                        SettingsRepository.SORT_OLDEST to "Terlama dulu",
                        SettingsRepository.SORT_TITLE  to "Judul A–Z"
                    )
                    sorts.forEachIndexed { i, (value, label) ->
                        if (i > 0) HorizontalDivider(color = MaterialTheme.colorScheme.background, thickness = 1.dp)
                        SettingsOption(
                            label    = label,
                            selected = uiState.sortOrder == value,
                            onClick  = { viewModel.setSortOrder(value) }
                        )
                    }
                }

                // ── Info ──────────────────────────────────────
                SettingsCard(
                    badge     = "Info",
                    badgeBg   = AppColors.CardYellow,
                    badgeText = AppColors.TagYellowText,
                    title     = "Tentang Aplikasi",
                    icon      = Icons.Filled.Info,
                    iconTint  = Color(0xFFF59E0B)
                ) {
                    InfoRow("Versi Aplikasi", "1.0.0")
                    HorizontalDivider(color = MaterialTheme.colorScheme.background, thickness = 1.dp)
                    InfoRow("Praktikum", "Minggu 7")
                    HorizontalDivider(color = MaterialTheme.colorScheme.background, thickness = 1.dp)
                    InfoRow("Data", "Offline-first (lokal)")
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SettingsCard(
    badge: String,
    badgeBg: Color,
    badgeText: Color,
    title: String,
    icon: ImageVector,
    iconTint: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Card header
        Row(
            modifier              = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(badgeBg)
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(badge, fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = badgeText)
            }
            Text(title, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.background, thickness = 1.5.dp)
        Column { content() }
    }
}

@Composable
private fun SettingsOption(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier              = Modifier
            .fillMaxWidth()
            .background(
                if (selected) AppColors.PrimaryLight.copy(alpha = 0.4f)
                else MaterialTheme.colorScheme.surface
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(
            label,
            fontSize   = 14.sp,
            color      = if (selected) AppColors.PrimaryDark else MaterialTheme.colorScheme.onSurface,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
        RadioButton(
            selected = selected,
            onClick  = onClick,
            colors   = RadioButtonDefaults.colors(
                selectedColor   = AppColors.Primary,
                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

@Composable
private fun InfoRow(label: String, value: String) {
    Row(
        modifier              = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
        Text(value, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}