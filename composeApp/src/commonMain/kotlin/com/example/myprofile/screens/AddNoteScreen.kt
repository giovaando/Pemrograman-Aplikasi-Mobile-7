package com.example.myprofile.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myprofile.theme.AppColors
import com.example.myprofile.viewmodel.NoteViewModel

@Composable
fun AddNoteScreen(viewModel: NoteViewModel, onBack: () -> Unit) {
    var title   by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .statusBarsPadding()
            ) {
                Row(
                    modifier              = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    // Back dengan teks "Notes"
                    TextButton(
                        onClick = onBack,
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack, null,
                            tint     = AppColors.Primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(
                            "Notes",
                            color      = AppColors.Primary,
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                    // Judul tengah
                    Text(
                        "New Note",
                        fontSize   = 17.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = MaterialTheme.colorScheme.onBackground
                    )
                    // Save button kanan
                    TextButton(
                        onClick  = {
                            if (title.isNotBlank()) {
                                viewModel.addNote(title.trim(), content.trim())
                                onBack()
                            }
                        },
                        enabled  = title.isNotBlank(),
                        contentPadding = PaddingValues(horizontal = 8.dp)
                    ) {
                        Text(
                            "Save",
                            color      = if (title.isNotBlank()) AppColors.Primary
                            else AppColors.NavInactive,
                            fontSize   = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
                HorizontalDivider(
                    color     = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    thickness = 0.5.dp
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Title field — borderless, besar
            androidx.compose.foundation.text.BasicTextField(
                value         = title,
                onValueChange = { title = it },
                modifier      = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                singleLine    = true,
                textStyle     = TextStyle(
                    fontSize   = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = MaterialTheme.colorScheme.onBackground
                ),
                decorationBox = { inner ->
                    if (title.isEmpty()) {
                        Text(
                            "Title",
                            fontSize   = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color      = AppColors.NavInactive
                        )
                    }
                    inner()
                }
            )
            HorizontalDivider(
                color     = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                thickness = 0.5.dp
            )
            // Content field — borderless, full size
            androidx.compose.foundation.text.BasicTextField(
                value         = content,
                onValueChange = { content = it },
                modifier      = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                textStyle     = TextStyle(
                    fontSize   = 16.sp,
                    color      = MaterialTheme.colorScheme.onBackground,
                    lineHeight = 24.sp
                ),
                decorationBox = { inner ->
                    if (content.isEmpty()) {
                        Text(
                            "Start typing your note...",
                            fontSize   = 16.sp,
                            color      = AppColors.NavInactive,
                            lineHeight = 24.sp
                        )
                    }
                    inner()
                }
            )
        }
    }
}