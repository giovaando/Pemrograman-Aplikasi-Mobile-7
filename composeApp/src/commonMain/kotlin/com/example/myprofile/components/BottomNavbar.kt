package com.example.myprofile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myprofile.navigation.Screen
import com.example.myprofile.theme.AppColors

private data class NavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
)

@Composable
fun BottomNavBar(navController: NavController) {
    // Perbaikan pemetaan Ikon agar sesuai dengan fungsinya
    val items = listOf(
        NavItem(Screen.Notes.route,     Icons.Filled.Home,      "Home"),
        NavItem(Screen.Favorites.route, Icons.Filled.Favorite,  "Favorites"),
        NavItem(Screen.Profile.route,   Icons.Filled.Person,    "Profile"),
        NavItem(Screen.Settings.route,  Icons.Filled.Settings,  "Settings"),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp), // Sedikit tambah vertical padding luar
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 20.dp,
                    shape = RoundedCornerShape(50.dp),
                    ambientColor = Color.Black.copy(alpha = 0.12f),
                    spotColor = Color.Black.copy(alpha = 0.12f)
                )
                .clip(RoundedCornerShape(50.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(horizontal = 10.dp, vertical = 10.dp) // Perbesar container sedikit
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val selected = currentRoute == item.route
                    NavPillItem(
                        item = item,
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(Screen.Notes.route) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun NavPillItem(
    item: NavItem,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(if (selected) AppColors.NavActive else Color.Transparent)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            )
            // ✅ Padding ditingkatkan untuk target sentuh yang lebih baik (Aksesibilitas)
            .padding(horizontal = if (selected) 24.dp else 18.dp, vertical = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.label,
            // ✅ Ukuran Ikon ditingkatkan dari 22.dp menjadi 26.dp
            modifier = Modifier.size(26.dp),
            tint = if (selected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}