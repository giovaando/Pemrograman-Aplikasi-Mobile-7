package com.example.myprofile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.theme.AppColors
import com.example.myprofile.theme.AppTheme

/**
 * COMPOSABLE 4 — SkillChip (updated)
 * Ditambahkan parameter theme untuk mendukung dark/light mode.
 */
@Composable
fun SkillChip(
    skill: String,
    theme: AppTheme,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(theme.divider, RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFF9FA8DA), RoundedCornerShape(20.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = skill,
            fontSize = 13.sp,
            color = AppColors.Primary,
            fontWeight = FontWeight.Medium
        )
    }
}