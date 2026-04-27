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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myprofile.theme.AppColors
import com.example.myprofile.ui.*
import com.example.myprofile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // ── Header avatar card (kuning) ───────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(AppColors.AccentYellow)
                    .padding(20.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Avatar circle with initial
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFD4BC00)),
                        contentAlignment = Alignment.Center
                    ) {
                        // Inner decorative ring
                        Box(
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(Color(0xFFFFCC00), Color(0xFFD4A000))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                uiState.name.take(1).uppercase(),
                                fontSize = 26.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = Color(0xFF1A1200)
                            )
                        }
                    }
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(
                            uiState.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF1A1200)
                        )
                        Text(
                            uiState.title,
                            fontSize = 13.sp,
                            color = Color(0xFF1A1200).copy(alpha = 0.65f),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // ── Stats row ─────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatItem(value = uiState.skills.size.toString(), label = "Skills")
                VerticalDivider(
                    modifier = Modifier.height(32.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )
                StatItem(value = "12", label = "Catatan")
                VerticalDivider(
                    modifier = Modifier.height(32.dp),
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )
                StatItem(value = "4", label = "Favorit")
            }

            Spacer(Modifier.height(12.dp))

            // ── Save success banner ───────────────────────────
            AnimatedVisibility(
                visible = uiState.saveSuccess,
                enter   = fadeIn() + slideInVertically(),
                exit    = fadeOut()
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = AppColors.AccentGreen),
                    shape  = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        "✅ Profil berhasil disimpan!",
                        color      = Color(0xFF001A08),
                        fontWeight = FontWeight.Medium,
                        modifier   = Modifier.padding(12.dp)
                    )
                }
            }

            // ── Bio card (green accent) ───────────────────────
            SectionLabel("TENTANG SAYA")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(AppColors.AccentGreen)
                            .padding(top = 4.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        uiState.bio,
                        fontSize   = 14.sp,
                        color      = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        lineHeight = 22.sp,
                        textAlign  = TextAlign.Justify
                    )
                }
            }

            // ── Skills card (purple accent chips) ────────────
            Spacer(Modifier.height(8.dp))
            SectionLabel("SKILLS")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Column {
                    uiState.skills.chunked(3).forEach { rowSkills ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            rowSkills.forEach { skill ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(AppColors.AccentPurple)
                                        .padding(horizontal = 14.dp, vertical = 7.dp)
                                ) {
                                    Text(skill, fontSize = 13.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = Color(0xFF0D0020))
                                }
                            }
                        }
                    }
                }
            }

            // ── Kontak ────────────────────────────────────────
            Spacer(Modifier.height(8.dp))
            SectionLabel("INFORMASI KONTAK")
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                AnimatedVisibility(
                    visible = uiState.showContact,
                    enter   = fadeIn() + slideInVertically(),
                    exit    = fadeOut()
                ) {
                    Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                        ContactRow(Icons.Filled.Email, uiState.email, AppColors.IconEmail)
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                        ContactRow(Icons.Filled.Phone, uiState.phone, AppColors.IconPhone)
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                        ContactRow(Icons.Filled.LocationOn, uiState.location, AppColors.IconLocation)
                        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
                        ContactRow(Icons.Filled.Star, uiState.github, AppColors.IconGithub)
                    }
                }
                if (!uiState.showContact) {
                    TextButton(
                        onClick = { viewModel.toggleContact() },
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Icon(Icons.Filled.Search, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(6.dp))
                        Text("Tampilkan Kontak")
                    }
                } else {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                        TextButton(onClick = { viewModel.toggleContact() }) {
                            Icon(Icons.Filled.Lock, contentDescription = null, modifier = Modifier.size(14.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Sembunyikan", fontSize = 12.sp)
                        }
                    }
                }
            }

            // ── Edit form ─────────────────────────────────────
            AnimatedVisibility(
                visible = uiState.isEditMode,
                enter   = fadeIn() + slideInVertically(),
                exit    = fadeOut() + slideOutVertically()
            ) {
                EditProfileForm(
                    editName     = uiState.editName,
                    editBio      = uiState.editBio,
                    onNameChange = { viewModel.onEditNameChange(it) },
                    onBioChange  = { viewModel.onEditBioChange(it) },
                    onSave       = { viewModel.saveProfile() },
                    onCancel     = { viewModel.closeEditMode() }
                )
            }

            // ── Action buttons ────────────────────────────────
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        if (uiState.isEditMode) viewModel.closeEditMode()
                        else viewModel.openEditMode()
                    },
                    modifier = Modifier.weight(1f),
                    shape    = RoundedCornerShape(14.dp),
                    colors   = ButtonDefaults.buttonColors(
                        containerColor = if (uiState.isEditMode)
                            MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Icon(Icons.Filled.Edit, null, Modifier.size(17.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        if (uiState.isEditMode) "Tutup Edit" else "Edit Profil",
                        fontWeight = FontWeight.Bold
                    )
                }
                OutlinedButton(
                    onClick  = {},
                    modifier = Modifier.weight(1f),
                    shape    = RoundedCornerShape(14.dp),
                    colors   = OutlinedButtonDefaults.colors(
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Icon(Icons.Filled.Share, null, Modifier.size(17.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Bagikan", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun StatItem(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onSurface)
        Text(label, fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.45f),
        modifier = Modifier.padding(start = 24.dp, top = 4.dp, bottom = 2.dp)
    )
}

@Composable
private fun ContactRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    tint: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(tint.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = tint, modifier = Modifier.size(16.dp))
        }
        Spacer(Modifier.width(12.dp))
        Text(value, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
    }
}
