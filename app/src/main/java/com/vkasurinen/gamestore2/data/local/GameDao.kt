package com.vkasurinen.gamestore2.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface GameDao {

    @Upsert
    suspend fun upsertGameList(gameList: List<GameEntity>)

    @Query("SELECT * FROM GameEntity WHERE id = :id")
    suspend fun getGameById(id: Int): GameEntity

    @Query("SELECT * FROM GameEntity")
    suspend fun getAllGames(): List<GameEntity>

    //vielä pitää korjata varmaankin että saadaan pelit kategorian mukaan

}