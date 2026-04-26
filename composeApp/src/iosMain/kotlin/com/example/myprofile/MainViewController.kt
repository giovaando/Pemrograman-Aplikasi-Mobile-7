package com.example.myprofile

import androidx.compose.ui.window.ComposeUIViewController
import com.example.myprofile.database.DatabaseDriverFactory

fun MainViewController() = ComposeUIViewController {
    App(driverFactory = DatabaseDriverFactory())
}