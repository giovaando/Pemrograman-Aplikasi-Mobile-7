package com.example.myprofile.model

data class ProfileData(
    val name: String,
    val title: String,
    val bio: String,
    val email: String,
    val phone: String,
    val location: String,
    val github: String,
    val skills: List<String>
)