package com.vkasurinen.gamestore2.data.mappers


import com.vkasurinen.gamestore2.data.local.GameEntity
import com.vkasurinen.gamestore2.data.local.GenreEntity
import com.vkasurinen.gamestore2.data.remote.response.GameDto
import com.vkasurinen.gamestore2.data.remote.response.GenreDto
import com.vkasurinen.gamestore2.domain.model.Game
import com.vkasurinen.gamestore2.domain.model.Genre

fun GameDto.toGameEntity(): GameEntity {
    return GameEntity(
        id = id,
        slug = slug,
        name = name,
        released = released,
        tba = tba,
        background_image = background_image,
        rating = rating,
        rating_top = rating_top,
        ratings_count = ratings_count,
        reviews_text_count = reviews_text_count,
        added = added,
        metacritic = metacritic,
        playtime = playtime,
        suggestions_count = suggestions_count,
        updated = updated,
        reviews_count = reviews_count,
        saturated_color = saturated_color,
        dominant_color = dominant_color,
        //genres = genres.map { it.toGenreEntity() }
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
        ratingTop = rating_top,
        ratingsCount = ratings_count,
        reviewsTextCount = reviews_text_count,
        added = added,
        metacritic = metacritic,
        playtime = playtime,
        suggestionsCount = suggestions_count,
        updated = updated,
        reviewsCount = reviews_count,
        saturatedColor = saturated_color,
        dominantColor = dominant_color,
        //genres = genres.map { it.toGenre() }
    )
}

fun GenreDto.toGenreEntity(): GenreEntity {
    return GenreEntity(
        id = id,
        name = name,
        slug = slug,
        games_count = games_count,
        background_image = background_image
    )
}

fun GenreEntity.toGenre(): Genre {
    return Genre(
        id = id,
        name = name,
        slug = slug,
        gamesCount = games_count,
        background_image = background_image
    )
}