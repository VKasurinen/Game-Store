package com.vkasurinen.gamestore2.data.remote.response

data class GenreDto(
    val id: Int?,
    val name: String?,
    val slug: String?,
    val games_count: Int?,
    val image_background: String?,
    val games: List<GameDto>
)