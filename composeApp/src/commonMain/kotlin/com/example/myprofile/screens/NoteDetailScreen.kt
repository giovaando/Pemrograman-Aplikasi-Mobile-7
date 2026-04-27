package com.example.myprofile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.theme.AppColors
import com.example.myprofile.viewmodel.NoteViewModel

@Composable
fun NoteDetailScreen(
    noteId:   Long,
    viewModel: NoteViewModel,
    onBack:   () -> Unit,
    onEdit:   (Long) -> Unit
) {
    LaunchedEffect(noteId) { viewModel.selectNote(noteId) }
    val note by viewModel.selectedNote.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            // Stitch-style: top bar dengan judul di tengah, back kiri, heart+trash kanan
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .statusBarsPadding()
            ) {
                Row(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    // Back
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, "Back",
                            tint     = AppColors.Primary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    // Title tengah
                    Text(
                        text       = note?.title ?: "",
                        fontSize   = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = MaterialTheme.colorScheme.onBackground,
                        maxLines   = 1
                    )
                    // Actions kanan: heart + trash
                    Row {
                        IconButton(onClick = { viewModel.toggleFavorite(noteId) }) {
                            Icon(
                                imageVector = if (note?.is_favorite == 1L)
                                    Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint     = if (note?.is_favorite == 1L)
                                    Color(0xFFEF4444) else MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        IconButton(onClick = { onEdit(noteId) }) {
                            Icon(
                                Icons.Filled.Edit, "Edit",
                                tint     = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }
                HorizontalDivider(
                    color     = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    thickness = 0.5.dp
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            // Judul besar
            Text(
                text       = note?.title ?: "Loading...",
                fontSize   = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 34.sp,
                color      = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(16.dp))
            // Konten
            Text(
                text       = note?.content ?: "",
                fontSize   = 16.sp,
                lineHeight = 26.sp,
                color      = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f)
            )
        }
    }
}