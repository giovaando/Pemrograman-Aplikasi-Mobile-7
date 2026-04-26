package com.example.myprofile.data

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.example.myprofile.db.NotesDatabase
import com.example.myprofile.db.NoteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock

class NoteRepository(private val database: NotesDatabase) {

    private val queries = database.noteQueries

    fun getAllNotes(): Flow<List<NoteEntity>> =
        queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.IO)

    fun getFavorites(): Flow<List<NoteEntity>> =
        queries.selectFavorites()
            .asFlow()
            .mapToList(Dispatchers.IO)

    fun searchNotes(query: String): Flow<List<NoteEntity>> {
        val like = "%$query%"
        return queries.selectByQuery(like, like)
            .asFlow()
            .mapToList(Dispatchers.IO)
    }

    fun getNoteById(id: Long): Flow<NoteEntity?> =
        queries.selectById(id)
            .asFlow()
            .mapToOneOrNull(Dispatchers.IO)

    suspend fun insertNote(title: String, content: String) {
        val now = Clock.System.now().toEpochMilliseconds()
        withContext(Dispatchers.IO) {
            queries.insert(title, content, now, now)
        }
    }

    suspend fun updateNote(id: Long, title: String, content: String) {
        val now = Clock.System.now().toEpochMilliseconds()
        withContext(Dispatchers.IO) {
            queries.update(title, content, now, id)
        }
    }

    suspend fun toggleFavorite(id: Long) {
        withContext(Dispatchers.IO) {
            queries.toggleFavorite(id)
        }
    }

    suspend fun deleteNote(id: Long) {
        withContext(Dispatchers.IO) {
            queries.delete(id)
        }
    }
}