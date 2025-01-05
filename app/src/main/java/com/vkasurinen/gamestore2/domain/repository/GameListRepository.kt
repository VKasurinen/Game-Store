package com.vkasurinen.gamestore2.domain.repository


import com.vkasurinen.gamestore2.domain.model.Game
import com.vkasurinen.gamestore2.domain.model.Genre
import com.vkasurinen.gamestore2.util.Resource
import kotlinx.coroutines.flow.Flow

interface GameListRepository {
    suspend fun getGameList(
        forceFetchFromRemote: Boolean,
        //genre: String,
        page: Int
    ): Flow<Resource<List<Game>>>

    suspend fun getGame(id: Int): Flow<Resource<Game>>

    suspend fun getAllGenres(forceFetchFromRemote: Boolean): Flow<Resource<List<Genre>>>

    suspend fun getGamesByGenre(genre: String, page: Int): Flow<Resource<List<Game>>>
}