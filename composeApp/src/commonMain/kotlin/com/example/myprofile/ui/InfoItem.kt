package com.example.myprofile.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.theme.AppColors

@Composable
fun InfoItem(
    icon: ImageVector,
    label: String,
    value: String,
    iconTint: Color = AppColors.IconDefault,
    modifier: Modifier = Modifier
) {
    Row(
        modifier          = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier         = Modifier
                .size(40.dp)
                .background(iconTint.copy(alpha = 0.12f), RoundedCornerShape(10.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector        = icon,
                contentDescription = label,
                tint               = iconTint,
                modifier           = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(14.dp))

        Column {
            Text(
                text          = label,
                fontSize      = 11.sp,
                color         = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight    = FontWeight.Medium,
                letterSpacing = 0.5.sp
            )
            Text(
                text     = value,
                fontSize = 15.sp,
                color    = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}