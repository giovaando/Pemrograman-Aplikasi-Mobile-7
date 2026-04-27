package com.example.myprofile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.theme.AppColors
import com.example.myprofile.viewmodel.NoteViewModel

@Composable
fun NoteDetailScreen(
    noteId: Long,
    viewModel: NoteViewModel,
    onBack: () -> Unit,
    onEdit: (Long) -> Unit
) {
    LaunchedEffect(noteId) { viewModel.selectNote(noteId) }
    val note by viewModel.selectedNote.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        // ✅ Aksi Utama dipindahkan ke Bawah (Bottom-Heavy UI)
        bottomBar = {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(16.dp),
                color = Color.Transparent
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(24.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Tombol Favorit
                    Button(
                        onClick = { viewModel.toggleFavorite(noteId) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (note?.is_favorite == 1L)
                                AppColors.CardPink.copy(alpha = 0.3f)
                            else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f).padding(end = 4.dp).height(52.dp)
                    ) {
                        Icon(
                            imageVector = if (note?.is_favorite == 1L) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorit",
                            tint = if (note?.is_favorite == 1L) Color.Red else MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            if (note?.is_favorite == 1L) "Favorit" else "Sukai",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    // Tombol Edit Utama
                    Button(
                        onClick = { onEdit(noteId) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.NavActive,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f).padding(start = 4.dp).height(52.dp)
                    ) {
                        Icon(Icons.Filled.Edit, "Edit", modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Edit Catatan", fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar minimalis hanya untuk tombol Back
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding() // ✅ Aman dari status bar
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", modifier = Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSurface)
                }
            }

            // Konten teks
            Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)) {
                Text(
                    text = note?.title ?: "Memuat...",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 34.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = note?.content ?: "",
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }
        }
    }
}