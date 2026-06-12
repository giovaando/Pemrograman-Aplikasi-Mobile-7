package com.example.myprofile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.data.SettingsRepository
import com.example.myprofile.theme.AppColors
import com.example.myprofile.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    // FIX: padding() tidak mendukung campuran horizontal + top + bottom
                    // Gunakan padding(start, top, end, bottom) sebagai gantinya
                    .padding(start = 20.dp, top = 16.dp, end = 20.dp, bottom = 8.dp)
            ) {
                Text("Pengaturan",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground)
                Text("Sesuaikan aplikasi Anda",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // ── Theme Section ─────────────────────────────────
            SettingsSectionCard(
                label  = "Tema",
                accent = AppColors.AccentYellow,
                title  = "Tampilan Aplikasi"
            ) {
                val themes = listOf(
                    SettingsRepository.THEME_SYSTEM to "Ikuti Sistem",
                    SettingsRepository.THEME_LIGHT  to "Terang",
                    SettingsRepository.THEME_DARK   to "Gelap"
                )
                themes.forEach { (value, label) ->
                    SettingsRadioRow(
                        label    = label,
                        selected = uiState.theme == value,
                        accent   = AppColors.AccentPurple,
                        onClick  = { viewModel.setTheme(value) }
                    )
                }
            }

            // ── Sort Order Section ────────────────────────────
            SettingsSectionCard(
                label  = "Urutan",
                accent = AppColors.AccentGreen,
                title  = "Tampilkan Catatan"
            ) {
                val sortOptions = listOf(
                    SettingsRepository.SORT_NEWEST to "Terbaru dulu",
                    SettingsRepository.SORT_OLDEST to "Terlama dulu",
                    SettingsRepository.SORT_TITLE  to "Judul A-Z"
                )
                sortOptions.forEach { (value, label) ->
                    SettingsRadioRow(
                        label    = label,
                        selected = uiState.sortOrder == value,
                        accent   = AppColors.AccentPurple,
                        onClick  = { viewModel.setSortOrder(value) }
                    )
                }
            }

            // ── Info card ─────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(18.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(AppColors.AccentOrange.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Filled.Info, null, tint = AppColors.AccentOrange,
                            modifier = Modifier.size(18.dp))
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("Notes App v1.0.0", fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface)
                        Text("Tugas Praktikum Minggu 7 · Offline-first",
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsSectionCard(
    label: String,
    accent: Color,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(accent)
                        .padding(horizontal = 12.dp, vertical = 5.dp)
                ) {
                    Text(label, fontSize = 12.sp, fontWeight = FontWeight.Bold,
                        color = Color(0xFF0D0020))
                }
                Spacer(Modifier.width(10.dp))
                Text(title, fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(Modifier.height(12.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            Spacer(Modifier.height(4.dp))
            content()
        }
    }
}

@Composable
private fun SettingsRadioRow(
    label: String,
    selected: Boolean,
    accent: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(interactionSource = interactionSource, indication = null, onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f))
        // Custom circle indicator
        Box(
            modifier = Modifier
                .size(22.dp)
                .clip(CircleShape)
                .background(
                    if (selected) accent
                    else MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                ),
            contentAlignment = Alignment.Center
        ) {
            if (selected) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0D0020))
                )
            }
        }
    }
}