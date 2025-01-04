package com.vkasurinen.gamestore2.data.remote.response

data class GenreDto(
    val id: Int,
    val name: String,
    val slug: String,
    val games_count: Int,
    val background_image: String,
    val games: List<GameDto>
)