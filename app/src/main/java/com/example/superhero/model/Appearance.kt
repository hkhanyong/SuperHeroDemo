package com.example.superhero.model

import androidx.compose.runtime.Stable

@Stable
data class Appearance(
    val gender: String,
    val race: String,
    val height: List<String>,
    val weight:List<String>,
)
