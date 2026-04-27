package com.example.myprofile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.myprofile.navigation.Screen
import com.example.myprofile.theme.AppColors

private data class NavItem(
    val route: String,
    val iconSelected: ImageVector,
    val iconUnselected: ImageVector,
    val label: String
)

@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        NavItem(Screen.Notes.route,     Icons.Filled.Home,         Icons.Outlined.Home,         "Notes"),
        NavItem(Screen.Favorites.route, Icons.Filled.Favorite,     Icons.Outlined.FavoriteBorder, "Favorites"),
        NavItem(Screen.Profile.route,   Icons.Filled.Person,       Icons.Outlined.Person,       "Profile"),
        NavItem(Screen.Settings.route,  Icons.Filled.Settings,     Icons.Outlined.Settings,     "Settings"),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Surface(
        modifier = Modifier.fillMaxWidth(),
        color    = MaterialTheme.colorScheme.surface,
        shadowElevation = 0.dp,
        tonalElevation  = 0.dp
    ) {
        // Divider tipis di atas
        HorizontalDivider(
            color     = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
            thickness = 0.5.dp
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val selected = currentRoute == item.route
                NavTabItem(
                    item     = item,
                    selected = selected,
                    onClick  = {
                        navController.navigate(item.route) {
                            popUpTo(Screen.Notes.route) { saveState = true }
                            launchSingleTop = true
                            restoreState    = true
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun NavTabItem(
    item:     NavItem,
    selected: Boolean,
    onClick:  () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication        = null,
                onClick           = onClick
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        Icon(
            imageVector        = if (selected) item.iconSelected else item.iconUnselected,
            contentDescription = item.label,
            modifier           = Modifier.size(24.dp),
            tint               = if (selected) AppColors.NavActive else AppColors.NavInactive
        )
        Text(
            text       = item.label,
            fontSize   = 10.sp,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal,
            color      = if (selected) AppColors.NavActive else AppColors.NavInactive
        )
    }
}