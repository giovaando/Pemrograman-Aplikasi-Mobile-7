package com.example.myprofile

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform