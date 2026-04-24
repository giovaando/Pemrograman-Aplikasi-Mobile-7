package com.example.myprofile.data

import com.example.myprofile.model.Note

class NoteRepository {
    // In-memory storage (sesuai scope tugas)
    private val notes = mutableListOf(
        Note(1, "Belajar Kotlin", "Kotlin adalah bahasa pemrograman modern yang berjalan di JVM dan bisa digunakan untuk Android development.", false, "2025-04-10"),
        Note(2, "Compose Multiplatform", "Compose Multiplatform memungkinkan kita membangun UI satu kali dan menjalankannya di Android, iOS, dan Desktop.", true, "2025-04-11"),
        Note(3, "State Management", "State di Compose dikelola dengan remember + mutableStateOf atau StateFlow dari ViewModel.", false, "2025-04-12"),
        Note(4, "Navigation Component", "NavHost, NavController, dan Routes adalah komponen utama navigasi di Compose.", true, "2025-04-13"),
        Note(5, "MVVM Architecture", "Model-View-ViewModel memisahkan logika bisnis dari UI, membuat kode lebih mudah di-test.", false, "2025-04-14"),
    )
    private var nextId = 6

    fun getAllNotes(): List<Note> = notes.toList()

    fun getNoteById(id: Int): Note? = notes.find { it.id == id }

    fun addNote(title: String, content: String): Note {
        val note = Note(
            id = nextId++,
            title = title,
            content = content,
            isFavorite = false,
            createdAt = "2025-04-17"
        )
        notes.add(note)
        return note
    }

    fun updateNote(id: Int, title: String, content: String) {
        val index = notes.indexOfFirst { it.id == id }
        if (index >= 0) {
            notes[index] = notes[index].copy(title = title, content = content)
        }
    }

    fun toggleFavorite(id: Int) {
        val index = notes.indexOfFirst { it.id == id }
        if (index >= 0) {
            notes[index] = notes[index].copy(isFavorite = !notes[index].isFavorite)
        }
    }

    fun deleteNote(id: Int) {
        notes.removeAll { it.id == id }
    }

    fun getFavorites(): List<Note> = notes.filter { it.isFavorite }
}