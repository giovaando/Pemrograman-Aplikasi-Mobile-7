package com.example.myprofile.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myprofile.theme.AppColors
import com.example.myprofile.ui.EditProfileForm
import com.example.myprofile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
        ) {
            // ── Hero header ───────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                    .background(AppColors.CardYellow)
                    .padding(horizontal = 20.dp, vertical = 24.dp)
            ) {
                Column {
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Text(
                            "Profil Saya",
                            fontSize   = 13.sp,
                            color      = AppColors.TagYellowText,
                            fontWeight = FontWeight.Medium
                        )
                        IconButton(
                            onClick  = {
                                if (uiState.isEditMode) viewModel.closeEditMode()
                                else viewModel.openEditMode()
                            },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.6f))
                        ) {
                            Icon(
                                Icons.Filled.Edit, "Edit",
                                tint     = AppColors.TagYellowText,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Spacer(Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(Color.White.copy(alpha = 0.5f))
                                .then(
                                    Modifier.clip(CircleShape)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.Person, null,
                                modifier = Modifier.size(36.dp),
                                tint     = AppColors.TagYellowText
                            )
                        }
                        Column {
                            Text(
                                uiState.name,
                                fontSize   = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color      = AppColors.TagYellowText
                            )
                            Spacer(Modifier.height(4.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(Color.White.copy(alpha = 0.5f))
                                    .padding(horizontal = 10.dp, vertical = 3.dp)
                            ) {
                                Text(
                                    uiState.title,
                                    fontSize = 11.sp,
                                    color    = AppColors.TagYellowText
                                )
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // ── Save success ──────────────────────────────────
            AnimatedVisibility(
                visible = uiState.saveSuccess,
                enter   = fadeIn() + slideInVertically(),
                exit    = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(AppColors.CardMint)
                        .padding(14.dp)
                ) {
                    Text("Profil berhasil disimpan!", color = AppColors.TagMintText, fontWeight = FontWeight.Medium, fontSize = 13.sp)
                }
            }

            // ── Edit form ─────────────────────────────────────
            AnimatedVisibility(
                visible = uiState.isEditMode,
                enter   = fadeIn() + slideInVertically(),
                exit    = fadeOut() + slideOutVertically()
            ) {
                EditProfileForm(
                    editName    = uiState.editName,
                    editBio     = uiState.editBio,
                    onNameChange = { viewModel.onEditNameChange(it) },
                    onBioChange  = { viewModel.onEditBioChange(it) },
                    onSave       = { viewModel.saveProfile() },
                    onCancel     = { viewModel.closeEditMode() }
                )
            }

            Spacer(Modifier.height(8.dp))

            // ── Bio card ──────────────────────────────────────
            ProfileInfoCard(
                modifier = Modifier.padding(horizontal = 20.dp),
                accentColor = AppColors.Primary,
                label   = "Tentang Saya"
            ) {
                Text(
                    uiState.bio,
                    fontSize   = 13.sp,
                    lineHeight = 20.sp,
                    color      = MaterialTheme.colorScheme.onSurface,
                    textAlign  = TextAlign.Justify
                )
            }

            Spacer(Modifier.height(12.dp))

            // ── Kontak ────────────────────────────────────────
            ProfileInfoCard(
                modifier    = Modifier.padding(horizontal = 20.dp),
                accentColor = Color(0xFF10B981),
                label       = "Informasi Kontak",
                action      = {
                    TextButton(onClick = { viewModel.toggleContact() }) {
                        Text(
                            if (uiState.showContact) "Sembunyikan" else "Tampilkan",
                            fontSize = 11.sp,
                            color    = AppColors.Primary
                        )
                    }
                }
            ) {
                AnimatedVisibility(visible = uiState.showContact, enter = fadeIn(), exit = fadeOut()) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        ContactRow(Icons.Filled.Email,      "Email",    uiState.email,    AppColors.IconEmail)
                        ContactRow(Icons.Filled.Phone,      "Telepon",  uiState.phone,    AppColors.IconPhone)
                        ContactRow(Icons.Filled.LocationOn, "Lokasi",   uiState.location, AppColors.IconLocation)
                        ContactRow(Icons.Filled.Star,       "GitHub",   uiState.github,   AppColors.IconGithub)
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            // ── Skills ────────────────────────────────────────
            ProfileInfoCard(
                modifier    = Modifier.padding(horizontal = 20.dp),
                accentColor = Color(0xFFF59E0B),
                label       = "Skills"
            ) {
                androidx.compose.foundation.layout.FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement   = Arrangement.spacedBy(6.dp)
                ) {
                    uiState.skills.forEachIndexed { i, skill ->
                        val bg   = if (i < 2) AppColors.PrimaryLight else MaterialTheme.colorScheme.surfaceVariant
                        val text = if (i < 2) AppColors.PrimaryDark  else MaterialTheme.colorScheme.onSurfaceVariant
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(bg)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(skill, fontSize = 12.sp, color = text, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileInfoCard(
    modifier: Modifier = Modifier,
    accentColor: Color,
    label: String,
    action: @Composable (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .width(4.dp).height(16.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(accentColor)
                )
                Text(label, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
            }
            action?.invoke()
        }
        Spacer(Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun ContactRow(icon: ImageVector, label: String, value: String, tint: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(tint.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, null, tint = tint, modifier = Modifier.size(18.dp))
        }
        Column {
            Text(label, fontSize = 10.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}