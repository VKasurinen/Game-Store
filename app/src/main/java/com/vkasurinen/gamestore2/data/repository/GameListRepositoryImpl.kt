package com.vkasurinen.gamestore2.data.repository


import android.os.Build
import androidx.annotation.RequiresExtension
import com.vkasurinen.gamestore2.data.local.GameDatabase
import com.vkasurinen.gamestore2.data.mappers.toGame
import com.vkasurinen.gamestore2.data.mappers.toGameEntity
import com.vkasurinen.gamestore2.data.mappers.toGenre
import com.vkasurinen.gamestore2.data.mappers.toGenreEntity
import com.vkasurinen.gamestore2.data.remote.GameApi
import com.vkasurinen.gamestore2.domain.model.Game
import com.vkasurinen.gamestore2.domain.model.Genre
import com.vkasurinen.gamestore2.domain.repository.GameListRepository
import com.vkasurinen.gamestore2.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException

class GameListRepositoryImpl(
    private val gameApi: GameApi,
    private val gameDatabase: GameDatabase
) : GameListRepository {


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getGameList(
        forceFetchFromRemote: Boolean,
        page: Int
    ): Flow<Resource<List<Game>>> {

        return flow {
            emit(Resource.Loading(true))
            val localGameList = gameDatabase.gameDao.getAllGames()

            val shouldLoadLocalGame = localGameList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalGame) {
                emit(Resource.Success(
                    data = localGameList.map { gameEntity ->
                        gameEntity.toGame()
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val gameListFromApi = try {
                gameApi.getGamesList(page = page)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading games"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading games"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading games"))
                return@flow
            }

            val gameEntities = gameListFromApi.results.let {
                it.map { gameDto ->
                    gameDto.toGameEntity()
                }
            }

            gameDatabase.gameDao.upsertGameList(gameEntities)

            emit(Resource.Success(
                gameEntities.map { it.toGame() }
            ))
            emit(Resource.Loading(false))
        }
    }
    override suspend fun getGame(id: Int): Flow<Resource<Game>> {
        return flow {
            emit(Resource.Loading(true))

            val gameEntity = gameDatabase.gameDao.getGameById(id)

            if (gameEntity != null) {
                emit(
                    Resource.Success(gameEntity.toGame())
                )

                emit(Resource.Loading(false))
                return@flow
            }

            emit(Resource.Error("Error no such game"))

            emit(Resource.Loading(false))
        }
    }

    override suspend fun getAllGenres(forceFetchFromRemote: Boolean): Flow<Resource<List<Genre>>> {
        return flow {
            emit(Resource.Loading(true))
            val localGenreList = gameDatabase.genreDao.getAllGenres()

            val shouldLoadLocalGenres = localGenreList.isNotEmpty() && !forceFetchFromRemote

            if (shouldLoadLocalGenres) {
                emit(Resource.Success(
                    data = localGenreList.map { genreEntity ->
                        genreEntity.toGenre()
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val genreListFromApi = try {
                gameApi.getAllGenres()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading genres"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading genres"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading genres"))
                return@flow
            }

            val genreEntities = genreListFromApi.results.let {
                it.map { genreDto ->
                    genreDto.toGenreEntity()
                }
            }

            gameDatabase.genreDao.upsertGenreList(genreEntities)

            emit(Resource.Success(
                genreEntities.map { it.toGenre() }
            ))
            emit(Resource.Loading(false))
        }
    }
}