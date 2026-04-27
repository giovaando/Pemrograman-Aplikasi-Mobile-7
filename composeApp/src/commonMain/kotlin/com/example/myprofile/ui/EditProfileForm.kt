package com.example.myprofile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.theme.AppColors

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
    var showNameError by remember { mutableStateOf(false) }
    val isNameBlank   = editName.isBlank()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(Modifier.width(4.dp).height(16.dp).clip(RoundedCornerShape(2.dp)).background(AppColors.Primary))
            Text("Edit Profil", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground)
        }

        // Nama field
        Column {
            Text(
                "Nama",
                fontSize   = 11.sp,
                fontWeight = FontWeight.Medium,
                color      = if (showNameError && isNameBlank) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value         = editName,
                onValueChange = { onNameChange(it); if (showNameError) showNameError = false },
                isError       = showNameError && isNameBlank,
                supportingText = {
                    if (showNameError && isNameBlank)
                        Text("Nama tidak boleh kosong", fontSize = 11.sp, color = MaterialTheme.colorScheme.error)
                },
                singleLine = true,
                modifier   = Modifier.fillMaxWidth(),
                shape      = RoundedCornerShape(12.dp),
                colors     = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = AppColors.Primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )
        }

        // Bio field
        Column {
            Text("Bio", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(4.dp))
            OutlinedTextField(
                value         = editBio,
                onValueChange = onBioChange,
                modifier      = Modifier.fillMaxWidth(),
                shape         = RoundedCornerShape(12.dp),
                maxLines      = 4,
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = AppColors.Primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(
                onClick  = onCancel,
                modifier = Modifier.weight(1f),
                shape    = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.Close, null, Modifier.size(14.dp))
                Spacer(Modifier.width(4.dp))
                Text("Batal", fontSize = 13.sp)
            }
            Button(
                onClick = {
                    if (isNameBlank) showNameError = true
                    else { showNameError = false; onSave() }
                },
                modifier = Modifier.weight(1f),
                shape    = RoundedCornerShape(12.dp),
                colors   = ButtonDefaults.buttonColors(containerColor = AppColors.NavActive)
            ) {
                Icon(Icons.Filled.Check, null, Modifier.size(14.dp))
                Spacer(Modifier.width(4.dp))
                Text("Simpan", fontWeight = FontWeight.SemiBold, fontSize = 13.sp)
            }
        }
    }
}