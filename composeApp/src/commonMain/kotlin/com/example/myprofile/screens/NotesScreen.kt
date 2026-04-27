@file:OptIn(kotlin.time.ExperimentalTime::class)

package com.example.myprofile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Person
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
import com.example.myprofile.viewmodel.NoteViewModel
import kotlin.time.Instant

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
                    .statusBarsPadding()
                    .padding(horizontal = 20.dp)
                    .padding(top = 12.dp, bottom = 8.dp)
            ) {
                // Header row: title + avatar
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(
                        text       = "Notes",
                        fontSize   = 34.sp,
                        fontWeight = FontWeight.Bold,
                        color      = MaterialTheme.colorScheme.onBackground
                    )
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Outlined.Person, null,
                            modifier = Modifier.size(20.dp),
                            tint     = AppColors.Primary
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                // Search bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .padding(horizontal = 12.dp, vertical = 9.dp),
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        Icons.Filled.Search, null,
                        modifier = Modifier.size(17.dp),
                        tint     = AppColors.NavInactive
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
                                .size(15.dp)
                                .clickable { viewModel.onSearchQueryChange("") },
                            tint = AppColors.NavInactive
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick        = onAddClick,
                containerColor = AppColors.Primary,
                contentColor   = Color.White,
                shape          = CircleShape,
                modifier       = Modifier.size(58.dp)
            ) {
                Icon(Icons.Filled.Add, "Add Note", modifier = Modifier.size(26.dp))
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
                    modifier    = Modifier.fillMaxSize().padding(padding),
                    searchQuery = searchQuery
                )
            }
            is NotesUiState.Content -> {
                LazyVerticalStaggeredGrid(
                    columns             = StaggeredGridCells.Fixed(2),
                    modifier            = Modifier.fillMaxSize().padding(padding),
                    contentPadding      = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalItemSpacing = 10.dp
                ) {
                    itemsIndexed(
                        state.notes,
                        key  = { _, n -> n.id }
                    ) { _, note ->
                        NoteCard(
                            note             = note,
                            onClick          = { onNoteClick(note.id) },
                            onToggleFavorite = { viewModel.toggleFavorite(note.id) },
                            onDelete         = { viewModel.deleteNote(note.id) }
                        )
                    }
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Spacer(Modifier.height(80.dp))
                    }
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
                    "Search",
                    fontSize = 14.sp,
                    color    = AppColors.NavInactive
                )
            }
            inner()
        }
    )
}

@Composable
private fun NoteCard(
    note:             NoteEntity,
    onClick:          () -> Unit,
    onToggleFavorite: () -> Unit,
    onDelete:         () -> Unit
) {
    val dateStr = remember(note.updated_at) {
        try {
            val instant = Instant.fromEpochMilliseconds(note.updated_at)
            val str = instant.toString().take(10)
            // Format menjadi "Oct 26" style
            val parts = str.split("-")
            if (parts.size == 3) {
                val months = listOf("","Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec")
                val month  = parts[1].toIntOrNull()?.let { months.getOrNull(it) } ?: parts[1]
                val day    = parts[2].trimStart('0')
                "$month $day"
            } else str
        } catch (_: Exception) { "" }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = RoundedCornerShape(14.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 14.dp)
    ) {
        Column {
            // Title
            Text(
                text       = note.title,
                fontSize   = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color      = AppColors.Primary,
                maxLines   = 2,
                overflow   = TextOverflow.Ellipsis,
                lineHeight = 20.sp
            )
            if (note.content.isNotBlank()) {
                Spacer(Modifier.height(6.dp))
                Text(
                    text       = note.content,
                    fontSize   = 13.sp,
                    color      = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines   = 4,
                    overflow   = TextOverflow.Ellipsis,
                    lineHeight = 18.sp
                )
            }
            Spacer(Modifier.height(10.dp))
            // Footer: date + actions
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                Text(
                    text     = dateStr,
                    fontSize = 11.sp,
                    color    = AppColors.TextDate
                )
                Row(horizontalArrangement = Arrangement.spacedBy(0.dp)) {
                    IconButton(
                        onClick  = onToggleFavorite,
                        modifier = Modifier.size(26.dp)
                    ) {
                        Icon(
                            imageVector = if (note.is_favorite == 1L) Icons.Filled.Favorite
                            else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint     = if (note.is_favorite == 1L) Color(0xFFEF4444)
                            else AppColors.NavInactive,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                    IconButton(
                        onClick  = onDelete,
                        modifier = Modifier.size(26.dp)
                    ) {
                        Icon(
                            Icons.Filled.Delete, "Delete",
                            tint     = AppColors.NavInactive,
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState(modifier: Modifier, searchQuery: String) {
    Box(modifier, contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(AppColors.PrimaryLight),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Filled.Edit, null,
                    modifier = Modifier.size(28.dp),
                    tint     = AppColors.Primary
                )
            }
            Spacer(Modifier.height(16.dp))
            Text(
                text       = if (searchQuery.isBlank()) "No notes yet" else "Not found",
                fontSize   = 17.sp,
                fontWeight = FontWeight.SemiBold,
                color      = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(6.dp))
            Text(
                text     = if (searchQuery.isBlank()) "Tap + to start writing" else "Try a different keyword",
                fontSize = 14.sp,
                color    = AppColors.NavInactive
            )
        }
    }
}