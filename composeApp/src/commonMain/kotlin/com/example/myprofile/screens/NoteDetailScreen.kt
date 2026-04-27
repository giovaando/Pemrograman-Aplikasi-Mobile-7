package com.example.myprofile.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.viewmodel.NoteViewModel
import kotlinx.datetime.Instant

@OptIn(ExperimentalMaterial3Api::class)
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
        topBar = {
            TopAppBar(
                title = { Text("Detail Catatan") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    note?.let {
                        IconButton(onClick = { viewModel.toggleFavorite(it.id) }) {
                            Icon(
                                imageVector = if (it.is_favorite == 1L) Icons.Filled.Favorite
                                else Icons.Filled.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (it.is_favorite == 1L) MaterialTheme.colorScheme.error
                                else MaterialTheme.colorScheme.onSurface
                            )
                        }
                        IconButton(onClick = { onEdit(it.id) }) {
                            Icon(Icons.Filled.Edit, contentDescription = "Edit")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (note == null) {
            Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            val n = note!!
            val dateStr = remember(n.updated_at) {
                try { Instant.fromEpochMilliseconds(n.updated_at).toString().take(10) }
                catch (e: Exception) { "" }
            }
            Column(
                modifier = Modifier.fillMaxSize().padding(padding)
                    .padding(horizontal = 20.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(n.title, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                Spacer(Modifier.height(8.dp))
                Text(dateStr, fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f))
                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                Text(n.content, fontSize = 16.sp, lineHeight = 26.sp)
            }
        }
    }
}