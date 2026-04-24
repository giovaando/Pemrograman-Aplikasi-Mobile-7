package com.example.myprofile.model

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val isFavorite: Boolean = false,
    val createdAt: String = ""
)