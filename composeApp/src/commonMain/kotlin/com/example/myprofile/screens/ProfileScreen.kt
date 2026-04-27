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

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // ── Hero header ───────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                    .background(AppColors.CardYellow)
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp)
                    .padding(top = 20.dp, bottom = 32.dp)  // FIX: pisahkan padding agar tidak error
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.6f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.Person, null,
                            modifier = Modifier.size(50.dp),
                            tint = AppColors.TagYellowText
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = uiState.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.TagYellowText
                    )
                    Text(
                        text = uiState.title,
                        fontSize = 14.sp,
                        color = AppColors.TagYellowText.copy(alpha = 0.8f)
                    )

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {
                            if (uiState.isEditMode) viewModel.closeEditMode()
                            else viewModel.openEditMode()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.TagYellowText,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 10.dp)
                    ) {
                        Icon(
                            if (uiState.isEditMode) Icons.Filled.Close else Icons.Filled.Edit,
                            null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            if (uiState.isEditMode) "Batal Edit" else "Edit Profil",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

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
                    Text(
                        "Profil berhasil disimpan!",
                        color = AppColors.TagMintText,
                        fontWeight = FontWeight.Medium,
                        fontSize = 13.sp
                    )
                }
            }

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

            Spacer(Modifier.height(8.dp))

            ProfileInfoCard(
                modifier    = Modifier.padding(horizontal = 20.dp),
                accentColor = AppColors.Primary,
                label       = "Tentang Saya"
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

            ProfileInfoCard(
                modifier    = Modifier.padding(horizontal = 20.dp),
                accentColor = Color(0xFF10B981),
                label       = "Informasi Kontak",
                action      = {
                    TextButton(onClick = { viewModel.toggleContact() }) {
                        Text(
                            if (uiState.showContact) "Sembunyikan" else "Tampilkan",
                            fontSize   = 11.sp,
                            color      = AppColors.Primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            ) {
                AnimatedVisibility(
                    visible = uiState.showContact,
                    enter   = fadeIn(),
                    exit    = fadeOut()
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        ContactRow(Icons.Filled.Email,      "Email",   uiState.email,    AppColors.IconEmail)
                        ContactRow(Icons.Filled.Phone,      "Telepon", uiState.phone,    AppColors.IconPhone)
                        ContactRow(Icons.Filled.LocationOn, "Lokasi",  uiState.location, AppColors.IconLocation)
                        ContactRow(Icons.Filled.Star,       "GitHub",  uiState.github,   AppColors.IconGithub)
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            ProfileInfoCard(
                modifier    = Modifier.padding(horizontal = 20.dp),
                accentColor = Color(0xFFF59E0B),
                label       = "Skills"
            ) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement   = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.skills.forEachIndexed { i, skill ->
                        val bg   = if (i < 2) AppColors.PrimaryLight else MaterialTheme.colorScheme.surfaceVariant
                        val text = if (i < 2) AppColors.PrimaryDark  else MaterialTheme.colorScheme.onSurfaceVariant
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(bg)
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(skill, fontSize = 12.sp, color = text, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }

            Spacer(
                Modifier
                    .height(100.dp)
                    .navigationBarsPadding()
            )
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
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(16.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(accentColor)
                )
                Text(
                    label,
                    fontSize   = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = MaterialTheme.colorScheme.onBackground
                )
            }
            action?.invoke()
        }
        Spacer(Modifier.height(12.dp))
        content()
    }
}

@Composable
private fun ContactRow(icon: ImageVector, label: String, value: String, tint: Color) {
    Row(
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
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
            Text(label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
        }
    }
}