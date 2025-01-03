package com.vkasurinen.gamestore2.data.remote.response

data class GameDto(
    val id: Int,
    val slug: String,
    val name: String,
    val released: String,
    val tba: Boolean,
    val background_image: String,
    val rating: Double,
    val rating_top: Int,
    val ratings_count: Int,
    val reviews_text_count: Int,
    val added: Int,
    val metacritic: Int?,
    val playtime: Int,
    val suggestions_count: Int,
    val updated: String,
    val reviews_count: Int,
    val saturated_color: String,
    val dominant_color: String,
    //val genres: List<GenreDto>,
)