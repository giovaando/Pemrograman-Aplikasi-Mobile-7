package com.example.myprofile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.theme.AppColors
import com.example.myprofile.theme.AppTheme

/**
 * COMPOSABLE 1 — ProfileHeader (updated)
 * Ditambahkan Dark Mode Toggle Switch di pojok kanan atas.
 * State isDarkMode dan callback onToggleDarkMode di-hoist ke ViewModel.
 */
@Composable
fun ProfileHeader(
    name: String,
    title: String,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(AppColors.PrimaryDark, AppColors.Primary)
                )
            )
            .padding(vertical = 36.dp, horizontal = 16.dp)
    ) {
        // ── Dark Mode Toggle (pojok kanan atas) ───────────────
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 0.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = if (isDarkMode) Icons.Filled.DarkMode else Icons.Filled.LightMode,
                contentDescription = "Mode",
                tint = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.size(16.dp)
            )
            Switch(
                checked = isDarkMode,
                onCheckedChange = { onToggleDarkMode() },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFF5C6BC0),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color(0x55FFFFFF)
                ),
                modifier = Modifier.height(24.dp)
            )
        }

        // ── Konten Tengah ─────────────────────────────────────
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Foto profil circular
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(AppColors.Secondary)
                    .border(3.dp, Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Foto Profil",
                    modifier = Modifier.size(64.dp),
                    tint = Color.White
                )
            }

            Text(
                text = name,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .background(Color(0x44FFFFFF), RoundedCornerShape(20.dp))
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    color = Color(0xFFE8EAF6),
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}