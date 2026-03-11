package com.example.superhero.model

data class SuperHeroModel(
    val response: String,
    val id: String,
    val name: String,
    val appearance: Appearance,
    val powerstats: PowerStats,
    val work: Work,
    val image: Image
)

