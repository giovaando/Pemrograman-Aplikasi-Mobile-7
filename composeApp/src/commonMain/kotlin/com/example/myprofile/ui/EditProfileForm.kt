package com.example.myprofile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.theme.AppColors
import com.example.myprofile.theme.AppTheme

/**
 * COMPOSABLE — LabeledTextField
 * Stateless TextField dengan state hoisting.
 * State (value) dan event (onValueChange) dikirim dari parent.
 *
 * Ini contoh STATE HOISTING:
 *   Parent menyimpan state → turun ke sini sebagai parameter
 *   User ketik → onValueChange dipanggil → parent update state
 */
@Composable
fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    maxLines: Int = 1,
    theme: AppTheme,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = theme.textHint,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = placeholder,
                    color = theme.textHint,
                    fontSize = 14.sp
                )
            },
            maxLines = maxLines,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = AppColors.Primary,
                unfocusedBorderColor = theme.divider,
                focusedTextColor = theme.textPrimary,
                unfocusedTextColor = theme.textPrimary,
                cursorColor = AppColors.Primary,
                focusedContainerColor = theme.surface,
                unfocusedContainerColor = theme.surface
            )
        )
    }
}

/**
 * COMPOSABLE — EditProfileForm
 * Form edit profil yang stateless.
 * Semua state di-hoist ke ProfileViewModel melalui App().
 */
@Composable
fun EditProfileForm(
    editName: String,
    editBio: String,
    onNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    theme: AppTheme,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = theme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // ── Header ────────────────────────────────────────
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                Text(
                    text = "✏️ Edit Profil",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = AppColors.PrimaryDark,
                    modifier = Modifier.weight(1f)
                )
            }

            HorizontalDivider(color = theme.divider, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))

            // ── TextField Nama (State Hoisting) ───────────────
            LabeledTextField(
                label = "Nama",
                value = editName,
                onValueChange = onNameChange,
                placeholder = "Masukkan nama baru...",
                theme = theme
            )

            Spacer(modifier = Modifier.height(12.dp))

            // ── TextField Bio (State Hoisting) ────────────────
            LabeledTextField(
                label = "Bio",
                value = editBio,
                onValueChange = onBioChange,
                placeholder = "Masukkan bio baru...",
                maxLines = 4,
                theme = theme
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ── Action Buttons ────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Batal
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Batal",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Batal")
                }

                // Simpan
                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.Primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Simpan",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Simpan", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}