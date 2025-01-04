package com.vkasurinen.gamestore2.domain.model

data class Genre(
    val id: Int,
    val name: String,
    val slug: String,
    val gamesCount: Int,
    val background_image: String,
    val games: List<Game>
)
