package com.example.myprofile.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.data.SettingsRepository
import com.example.myprofile.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("⚙️ Pengaturan", fontWeight = FontWeight.Bold) })
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ── Theme Section ─────────────────────────────────
            SettingsSection(title = "Tema Aplikasi", icon = Icons.Filled.Star) {
                val themes = listOf(
                    SettingsRepository.THEME_SYSTEM to "Ikuti Sistem",
                    SettingsRepository.THEME_LIGHT  to "Terang",
                    SettingsRepository.THEME_DARK   to "Gelap"
                )
                themes.forEach { (value, label) ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = uiState.theme == value,
                            onClick = { viewModel.setTheme(value) }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(label, fontSize = 15.sp)
                    }
                }
            }

            // ── Sort Order Section ────────────────────────────
            SettingsSection(title = "Urutan Catatan", icon = Icons.Filled.List) {
                val sortOptions = listOf(
                    SettingsRepository.SORT_NEWEST to "Terbaru Dulu",
                    SettingsRepository.SORT_OLDEST to "Terlama Dulu",
                    SettingsRepository.SORT_TITLE  to "Judul A-Z"
                )
                sortOptions.forEach { (value, label) ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = uiState.sortOrder == value,
                            onClick = { viewModel.setSortOrder(value) }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(label, fontSize = 15.sp)
                    }
                }
            }

            // ── Info Section ──────────────────────────────────
            SettingsSection(title = "Tentang Aplikasi", icon = Icons.Filled.Info) {
                Text("Notes App v1.0.0", fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                Text("Tugas Praktikum Minggu 7", fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                Text("Data disimpan secara lokal (Offline-first)", fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
            }
        }
    }
}

@Composable
private fun SettingsSection(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            content()
        }
    }
}