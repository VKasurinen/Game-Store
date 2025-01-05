package com.vkasurinen.gamestore2.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GenreEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val slug: String,
    val games_count: Int,
    val image_background: String,
    val games: List<GameEntity>
)