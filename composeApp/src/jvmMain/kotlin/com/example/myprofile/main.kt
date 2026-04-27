package com.example.myprofile

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.myprofile.database.DatabaseDriverFactory

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Notes App") {
        App(driverFactory = DatabaseDriverFactory())
    }
}