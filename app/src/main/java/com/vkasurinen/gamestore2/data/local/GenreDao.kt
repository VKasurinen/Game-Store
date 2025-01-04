package com.vkasurinen.gamestore2.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface GenreDao {
    @Upsert
    suspend fun upsertGenreList(genreList: List<GenreEntity>)

    @Query("SELECT * FROM GenreEntity")
    suspend fun getAllGenres(): List<GenreEntity>
}