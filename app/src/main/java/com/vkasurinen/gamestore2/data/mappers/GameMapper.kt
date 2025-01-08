package com.vkasurinen.gamestore2.data.mappers


import com.vkasurinen.gamestore2.data.local.GameEntity
import com.vkasurinen.gamestore2.data.local.GenreEntity
import com.vkasurinen.gamestore2.data.remote.response.GameDto
import com.vkasurinen.gamestore2.data.remote.response.GenreDto
import com.vkasurinen.gamestore2.domain.model.Game
import com.vkasurinen.gamestore2.domain.model.Genre

fun GameDto.toGameEntity(): GameEntity {
    return GameEntity(
        id = id ?: -1,
        slug = slug ?: "",
        name = name ?: "",
        genreId = genreId ?: 0,
        released = released ?: "",
        tba = tba,
        background_image = background_image ?: "",
        rating = rating ?: 0.0,
        ratingTop = rating_top ?: 0,
        ratingsCount = ratings_count ?: 0,
        reviewsTextCount = reviews_text_count ?: 0,
        added = added ?: 0,
        metacritic = metacritic ?: 0,
        playtime = playtime ?: 0,
        suggestionsCount = suggestions_count ?: 0,
        updated = updated ?: "",
        reviewsCount = reviews_count ?: 0,
        saturatedColor = saturated_color ?: "",
        dominantColor = dominant_color ?: "",
        //genres = genres?.map { it.toGenreEntity() } ?: emptyList()
    )
}

fun GameEntity.toGame(): Game {
    return Game(
        id = id,
        slug = slug,
        name = name,
        released = released,
        tba = tba,
        background_image = background_image,
        rating = rating,
        ratingTop = ratingTop,
        ratingsCount = ratingsCount,
        reviewsTextCount = reviewsTextCount,
        added = added,
        metacritic = metacritic,
        playtime = playtime,
        suggestionsCount = suggestionsCount,
        updated = updated,
        reviewsCount = reviewsCount,
        saturatedColor = saturatedColor,
        dominantColor = dominantColor,
        //genres = genres.map { it.toGenre() }
    )
}

fun GenreDto.toGenreEntity(): GenreEntity {
    return GenreEntity(
        id = id ?: 0,
        name = name ?: "",
        slug = slug ?: "",
        games_count = games_count ?: 0,
        image_background = image_background ?: "",
        games = games.map { it.toGameEntity() } ?: emptyList()
    )
}

fun GenreEntity.toGenre(): Genre {
    return Genre(
        id = id,
        name = name,
        slug = slug,
        gamesCount = games_count,
        image_background = image_background,
        games = games.map { it.toGame() }
    )
}