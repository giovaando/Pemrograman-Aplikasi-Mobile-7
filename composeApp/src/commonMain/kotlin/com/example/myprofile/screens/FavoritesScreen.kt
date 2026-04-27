package com.example.myprofile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.myprofile.theme.AppColors
import com.example.myprofile.theme.cardColorForIndex
import com.example.myprofile.viewmodel.NoteViewModel
import kotlinx.datetime.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    viewModel: NoteViewModel,
    onNoteClick: (Long) -> Unit
) {
    val state by viewModel.favoritesState.collectAsState()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 20.dp)
                    .padding(top = 20.dp, bottom = 12.dp)
            ) {
                Text(
                    text     = "Koleksi",
                    fontSize = 13.sp,
                    color    = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text       = "Favorit",
                    fontSize   = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color      = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    ) { padding ->
        when (state) {
            is NotesUiState.Loading -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = AppColors.Primary)
                }
            }
            is NotesUiState.Empty -> {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(72.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFFBCFE8)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Filled.Favorite, null,
                                modifier = Modifier.size(32.dp),
                                tint     = Color(0xFF9D174D)
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "Belum ada favorit",
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Tandai catatan dengan hati",
                            fontSize  = 13.sp,
                            color     = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
            is NotesUiState.Content -> {
                val notes = (state as NotesUiState.Content).notes
                LazyColumn(
                    modifier       = Modifier.fillMaxSize().padding(padding),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    itemsIndexed(notes, key = { _, n -> n.id }) { index, note ->
                        val (cardBg, tagText) = cardColorForIndex(index)
                        val dateStr = remember(note.updated_at) {
                            try { Instant.fromEpochMilliseconds(note.updated_at).toString().take(10) }
                            catch (e: Exception) { "" }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(20.dp))
                                .background(cardBg)
                                .clickable { onNoteClick(note.id) }
                                .padding(horizontal = 16.dp, vertical = 14.dp)
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                    Icon(Icons.Filled.Favorite, null, tint = tagText, modifier = Modifier.size(14.dp))
                                    Text("Favorit", fontSize = 10.sp, fontWeight = FontWeight.SemiBold, color = tagText)
                                }
                                Spacer(Modifier.height(8.dp))
                                Text(note.title, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF111827), maxLines = 1, overflow = TextOverflow.Ellipsis)
                                Spacer(Modifier.height(4.dp))
                                Text(note.content, fontSize = 12.sp, color = Color(0xFF374151), maxLines = 2, overflow = TextOverflow.Ellipsis)
                                Spacer(Modifier.height(8.dp))
                                Text(dateStr, fontSize = 10.sp, color = tagText.copy(alpha = 0.7f))
                            }
                        }
                    }
                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
            else -> {}
        }
    }
}