package com.vkasurinen.gamestore2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameEntity(
    @PrimaryKey val id: Int,
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
    //@TypeConverters(GenreConverter::class) val genres: List<GenreEntity>,
)