package com.example.myprofile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.data.NotesUiState
import com.example.myprofile.db.NoteEntity
import com.example.myprofile.theme.AppColors
import com.example.myprofile.theme.cardColorForIndex
import com.example.myprofile.viewmodel.NoteViewModel
import kotlinx.datetime.Instant

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
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 8.dp)
            ) {
                Text(
                    text       = "Selamat datang",
                    fontSize   = 13.sp,
                    color      = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text       = "My Notes",
                    fontSize   = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color      = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(12.dp))
                // Search bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        Icons.Filled.Search, null,
                        modifier = Modifier.size(18.dp),
                        tint     = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    BasicSearchField(
                        value         = searchQuery,
                        onValueChange = { viewModel.onSearchQueryChange(it) },
                        modifier      = Modifier.weight(1f)
                    )
                    if (searchQuery.isNotEmpty()) {
                        Icon(
                            Icons.Filled.Close, null,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable { viewModel.onSearchQueryChange("") },
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick          = onAddClick,
                containerColor   = AppColors.NavActive,
                contentColor     = Color.White,
                shape            = CircleShape,
                modifier         = Modifier.size(56.dp)
            ) {
                Icon(Icons.Filled.Add, "Add Note", modifier = Modifier.size(24.dp))
            }
        }
    ) { padding ->
        when (val state = uiState) {
            is NotesUiState.Loading -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AppColors.Primary)
                }
            }
            is NotesUiState.Empty -> {
                EmptyState(
                    modifier      = Modifier.fillMaxSize().padding(padding),
                    searchQuery   = searchQuery
                )
            }
            is NotesUiState.Content -> {
                LazyColumn(
                    modifier        = Modifier.fillMaxSize().padding(padding),
                    contentPadding  = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Summary banner
                    item {
                        SummaryBanner(count = state.notes.size)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Catatan Terbaru",
                            fontSize   = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(Modifier.height(4.dp))
                    }
                    itemsIndexed(state.notes, key = { _, n -> n.id }) { index, note ->
                        NoteCard(
                            note             = note,
                            colorIndex       = index,
                            onClick          = { onNoteClick(note.id) },
                            onToggleFavorite = { viewModel.toggleFavorite(note.id) },
                            onDelete         = { viewModel.deleteNote(note.id) }
                        )
                    }
                    item { Spacer(Modifier.height(72.dp)) }
                }
            }
            is NotesUiState.Error -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Text(state.message, color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
private fun BasicSearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.text.BasicTextField(
        value         = value,
        onValueChange = onValueChange,
        modifier      = modifier,
        singleLine    = true,
        textStyle     = androidx.compose.ui.text.TextStyle(
            fontSize = 14.sp,
            color    = MaterialTheme.colorScheme.onBackground
        ),
        decorationBox = { inner ->
            if (value.isEmpty()) {
                Text(
                    "Cari catatan...",
                    fontSize = 14.sp,
                    color    = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            inner()
        }
    )
}

@Composable
private fun SummaryBanner(count: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(AppColors.PrimaryLight)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text       = "Tersimpan",
                    fontSize   = 11.sp,
                    color      = AppColors.PrimaryDark,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text       = "$count catatan aktif",
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color      = AppColors.PrimaryDark
                )
            }
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .background(AppColors.Primary.copy(alpha = 0.3f))
            )
        }
    }
}

@Composable
private fun NoteCard(
    note: NoteEntity,
    colorIndex: Int,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit
) {
    val (cardBg, tagText) = cardColorForIndex(colorIndex)

    val dateStr = remember(note.updated_at) {
        try { Instant.fromEpochMilliseconds(note.updated_at).toString().take(10) }
        catch (e: Exception) { "" }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(cardBg)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Column {
            // Tag row
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(tagText.copy(alpha = 0.12f))
                        .padding(horizontal = 10.dp, vertical = 3.dp)
                ) {
                    Text(
                        text       = if (note.is_favorite == 1L) "Favorit" else "Catatan",
                        fontSize   = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = tagText
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    IconButton(
                        onClick  = onToggleFavorite,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (note.is_favorite == 1L) Icons.Filled.Favorite
                            else Icons.Filled.FavoriteBorder,
                            contentDescription = "Fav",
                            tint     = tagText,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    IconButton(
                        onClick  = onDelete,
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            Icons.Filled.Delete, "Delete",
                            tint     = tagText.copy(alpha = 0.6f),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text       = note.title,
                fontSize   = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color      = Color(0xFF111827),
                maxLines   = 1,
                overflow   = TextOverflow.Ellipsis
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text     = note.content,
                fontSize = 12.sp,
                color    = Color(0xFF374151),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 18.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text     = dateStr,
                fontSize = 10.sp,
                color    = tagText.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier, searchQuery: String) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(AppColors.PrimaryLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Edit, null,
                    modifier = Modifier.size(32.dp),
                    tint     = AppColors.Primary
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text       = if (searchQuery.isBlank()) "Belum ada catatan"
                else "Tidak ditemukan",
                fontSize   = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color      = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text     = if (searchQuery.isBlank()) "Tekan + untuk mulai menulis"
                else "Coba kata kunci lain",
                fontSize = 13.sp,
                color    = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}