package com.vkasurinen.gamestore2.data.remote

import com.vkasurinen.gamestore2.data.remote.response.GameListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApi {

    @GET("games")
    suspend fun getGamesList(
        @Query("key") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int = 10
    ): GameListDto

    /*@GET("genres")
    suspend fun getAllGenres(
        @Query("key") apiKey: String = API_KEY
    ): GenreListDto
     */

    @GET("games")
    suspend fun getGamesByGenre(
        @Query("key") apiKey: String = API_KEY,
        @Query("genres") genre: String,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int = 10
    ): GameListDto

    companion object {
        const val BASE_URL = "https://api.rawg.io/api/"
        const val API_KEY = "d189fe14fd79460c98b7cc5b4b6e6307"
    }
}