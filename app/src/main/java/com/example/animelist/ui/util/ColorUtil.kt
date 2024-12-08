package com.example.animelist.ui.util

fun hexToComposeColor(hex: String): androidx.compose.ui.graphics.Color {
    return androidx.compose.ui.graphics.Color(android.graphics.Color.parseColor(hex))
}
