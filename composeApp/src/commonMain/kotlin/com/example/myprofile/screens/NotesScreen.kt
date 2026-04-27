package com.example.myprofile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.data.NotesUiState
import com.example.myprofile.db.NoteEntity
import com.example.myprofile.theme.AppColors
import com.example.myprofile.viewmodel.NoteViewModel
import kotlinx.datetime.Instant

// Warna aksen kartu: kuning, hijau, ungu, pink (loop)
private val cardColors = listOf(
    AppColors.NoteYellow,
    AppColors.NoteGreen,
    AppColors.NotePurple,
    AppColors.NotePink
)

private val cardTextColors = listOf(
    Color(0xFF1A1A00),
    Color(0xFF001A08),
    Color(0xFF0D0020),
    Color(0xFF1A0010)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(
    viewModel: NoteViewModel,
    onNoteClick: (Long) -> Unit,
    onAddClick: () -> Unit
) {
    val uiState     by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            ) {
                Spacer(Modifier.height(8.dp))
                Text(
                    "Selamat datang kembali",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
                Text(
                    "My Notes",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(12.dp))

                // Search bar modern
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text("Cari catatan...", fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f))
                    },
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                                Icon(Icons.Filled.Close, contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                        focusedBorderColor = AppColors.AccentYellow,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface
                    )
                )
                Spacer(Modifier.height(8.dp))
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.onBackground,
                contentColor = MaterialTheme.colorScheme.background,
                shape = CircleShape,
                modifier = Modifier.size(56.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add Note", modifier = Modifier.size(28.dp))
            }
        }
    ) { padding ->
        when (val state = uiState) {
            is NotesUiState.Loading -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AppColors.AccentYellow)
                }
            }
            is NotesUiState.Empty -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .background(AppColors.AccentYellow.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Filled.Info, null,
                                modifier = Modifier.size(36.dp),
                                tint = AppColors.AccentYellow)
                        }
                        Spacer(Modifier.height(16.dp))
                        Text(
                            if (searchQuery.isBlank()) "Belum ada catatan.\nTekan + untuk menambah."
                            else "Tidak ditemukan catatan\nuntuk \"$searchQuery\"",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            fontSize = 15.sp
                        )
                    }
                }
            }
            is NotesUiState.Content -> {
                Column(modifier = Modifier.padding(padding)) {
                    // Summary card
                    val noteCount = state.notes.size
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 4.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(AppColors.AccentPurple)
                            .padding(16.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Tersimpan hari ini", fontSize = 12.sp,
                                    color = Color(0xFF0D0020).copy(alpha = 0.7f))
                                Text("$noteCount catatan\naktif",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color(0xFF0D0020),
                                    lineHeight = 24.sp)
                            }
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF6B4FCC))
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))
                    Text("Catatan Terbaru",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                        color = MaterialTheme.colorScheme.onBackground)

                    NotesList(
                        notes = state.notes,
                        modifier = Modifier,
                        onNoteClick = onNoteClick,
                        onToggleFavorite = { viewModel.toggleFavorite(it) },
                        onDelete = { viewModel.deleteNote(it) }
                    )
                }
            }
            is NotesUiState.Error -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text("Error: ${state.message}", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
private fun NotesList(
    notes: List<NoteEntity>,
    modifier: Modifier = Modifier,
    onNoteClick: (Long) -> Unit,
    onToggleFavorite: (Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(notes, key = { it.id }) { note ->
            val colorIndex = (note.id % cardColors.size).toInt()
            NoteCard(
                note = note,
                accentColor = cardColors[colorIndex],
                textColor = cardTextColors[colorIndex],
                onClick = { onNoteClick(note.id) },
                onToggleFavorite = { onToggleFavorite(note.id) },
                onDelete = { onDelete(note.id) }
            )
        }
    }
}

@Composable
private fun NoteCard(
    note: NoteEntity,
    accentColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    val dateStr = remember(note.updated_at) {
        try {
            val instant = Instant.fromEpochMilliseconds(note.updated_at)
            val str = instant.toString()
            // Tampilkan "Hari ini" / tanggal sederhana
            str.take(10)
        } catch (e: Exception) { "" }
    }

    // Kategori dari tag/category field jika ada, fallback ke huruf awal
    val category = note.title.split(" ").firstOrNull()?.uppercase() ?: "NOTE"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(accentColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Category label chip
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .background(textColor.copy(alpha = 0.12f))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(category, fontSize = 10.sp, fontWeight = FontWeight.Bold,
                        color = textColor.copy(alpha = 0.7f), letterSpacing = 1.sp)
                }
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = onToggleFavorite,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = if (note.is_favorite == 1L) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = textColor.copy(alpha = 0.6f),
                        modifier = Modifier.size(16.dp)
                    )
                }
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(Icons.Filled.Delete, contentDescription = "Delete",
                        tint = textColor.copy(alpha = 0.45f),
                        modifier = Modifier.size(16.dp))
                }
            }
            Spacer(Modifier.height(6.dp))
            Text(
                note.title,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp,
                color = textColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Text(
                note.content,
                fontSize = 13.sp,
                color = textColor.copy(alpha = 0.65f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(dateStr, fontSize = 11.sp, color = textColor.copy(alpha = 0.5f))
        }
    }
}
