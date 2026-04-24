package com.example.myprofile.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myprofile.theme.AppColors
import com.example.myprofile.theme.AppTheme
import com.example.myprofile.ui.*
import com.example.myprofile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val theme = AppTheme(isDark = uiState.isDarkMode)

    Surface(modifier = Modifier.fillMaxSize(), color = theme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            ProfileHeader(
                name = uiState.name,
                title = uiState.title,
                isDarkMode = uiState.isDarkMode,
                onToggleDarkMode = { viewModel.toggleDarkMode() }
            )
            Spacer(Modifier.height(8.dp))

            AnimatedVisibility(
                visible = uiState.saveSuccess,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF43A047)),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        "✅ Profil berhasil disimpan!",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            ProfileCard("Tentang Saya", Icons.Filled.Person, theme) {
                Text(uiState.bio, fontSize = 14.sp, color = theme.textSecondary, lineHeight = 22.sp, textAlign = TextAlign.Justify)
            }

            AnimatedVisibility(
                visible = uiState.isEditMode,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
                EditProfileForm(
                    editName = uiState.editName,
                    editBio = uiState.editBio,
                    onNameChange = { viewModel.onEditNameChange(it) },
                    onBioChange = { viewModel.onEditBioChange(it) },
                    onSave = { viewModel.saveProfile() },
                    onCancel = { viewModel.closeEditMode() },
                    theme = theme
                )
            }

            ProfileCard("Informasi Kontak", Icons.Filled.Info, theme) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = { viewModel.toggleContact() }) {
                        Icon(
                            if (uiState.showContact) Icons.Filled.Lock else Icons.Filled.Search,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(if (uiState.showContact) "Sembunyikan" else "Tampilkan", fontSize = 12.sp)
                    }
                }
                AnimatedVisibility(
                    visible = uiState.showContact,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut()
                ) {
                    Column {
                        InfoItem(Icons.Filled.Email,      "Email",    uiState.email,    AppColors.IconEmail,    theme)
                        HorizontalDivider(color = theme.background)
                        InfoItem(Icons.Filled.Phone,      "Phone",    uiState.phone,    AppColors.IconPhone,    theme)
                        HorizontalDivider(color = theme.background)
                        InfoItem(Icons.Filled.LocationOn, "Location", uiState.location, AppColors.IconLocation, theme)
                        HorizontalDivider(color = theme.background)
                        InfoItem(Icons.Filled.Star,       "GitHub",   uiState.github,   AppColors.IconGithub,  theme)
                    }
                }
            }

            ProfileCard("Skill & Teknologi", Icons.Filled.Star, theme) {
                uiState.skills.chunked(3).forEach { rowSkills ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) { rowSkills.forEach { SkillChip(it, theme) } }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { if (uiState.isEditMode) viewModel.closeEditMode() else viewModel.openEditMode() },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.isEditMode) Color(0xFF757575) else AppColors.Primary
                    )
                ) {
                    Icon(Icons.Filled.Edit, null, Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(if (uiState.isEditMode) "Tutup Edit" else "Edit Profil", fontWeight = FontWeight.SemiBold)
                }
                OutlinedButton(
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Filled.Share, null, Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Bagikan", fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}