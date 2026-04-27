package com.example.myprofile.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.theme.AppColors

@Composable
fun LabeledTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    maxLines: Int = 1,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text       = label,
            fontSize   = 12.sp,
            fontWeight = FontWeight.Medium,
            color      = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier   = Modifier.padding(bottom = 4.dp)
        )
        OutlinedTextField(
            value         = value,
            onValueChange = onValueChange,
            placeholder   = {
                Text(
                    text     = placeholder,
                    color    = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            },
            maxLines = maxLines,
            modifier = Modifier.fillMaxWidth(),
            shape    = RoundedCornerShape(10.dp),
            colors   = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = AppColors.Primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
            )
        )
    }
}

@Composable
fun EditProfileForm(
    editName: String,
    editBio: String,
    onNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Tampilkan error hanya setelah user pernah mencoba simpan dengan nama kosong
    var showNameError by remember { mutableStateOf(false) }
    val isNameBlank = editName.isBlank()

    Card(
        modifier  = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape     = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors    = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier          = Modifier.padding(bottom = 12.dp)
            ) {
                Text(
                    text       = "✏️ Edit Profil",
                    fontSize   = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color      = AppColors.PrimaryDark,
                    modifier   = Modifier.weight(1f)
                )
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
            Spacer(modifier = Modifier.height(12.dp))

            // ── Field Nama dengan validasi ────────────────────
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text       = "Nama",
                    fontSize   = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color      = if (showNameError && isNameBlank)
                        MaterialTheme.colorScheme.error
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier   = Modifier.padding(bottom = 4.dp)
                )
                OutlinedTextField(
                    value         = editName,
                    onValueChange = {
                        onNameChange(it)
                        // Reset error saat user mulai mengetik lagi
                        if (showNameError) showNameError = false
                    },
                    placeholder   = { Text("Masukkan nama baru...") },
                    isError       = showNameError && isNameBlank,
                    supportingText = {
                        if (showNameError && isNameBlank) {
                            Text(
                                "⚠️ Nama tidak boleh kosong",
                                color    = MaterialTheme.colorScheme.error,
                                fontSize = 12.sp
                            )
                        }
                    },
                    singleLine = true,
                    modifier   = Modifier.fillMaxWidth(),
                    shape      = RoundedCornerShape(10.dp),
                    colors     = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor   = AppColors.Primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        errorBorderColor     = MaterialTheme.colorScheme.error
                    )
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            LabeledTextField(
                label         = "Bio",
                value         = editBio,
                onValueChange = onBioChange,
                placeholder   = "Masukkan bio baru...",
                maxLines      = 4
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick  = onCancel,
                    modifier = Modifier.weight(1f),
                    shape    = RoundedCornerShape(10.dp)
                ) {
                    Icon(Icons.Filled.Close, "Batal", Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Batal")
                }
                Button(
                    onClick = {
                        if (isNameBlank) {
                            // Tampilkan pesan error, TIDAK simpan
                            showNameError = true
                        } else {
                            showNameError = false
                            onSave()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    shape    = RoundedCornerShape(10.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = AppColors.Primary)
                ) {
                    Icon(Icons.Filled.Check, "Simpan", Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Simpan", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}