package com.vkasurinen.gamestore2.presentation.onboarding

import androidx.annotation.DrawableRes
import com.vkasurinen.gamestore2.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int,
)

val pages = listOf(
    Page(
        title = "Discover Your Next Favorite Game",
        description = "Welcome to GameHub! Your ultimate destination for discovering, exploring, and playing the latest games. Search for games by genre, ratings, and reviews, and find your perfect match with just a tap.",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Dive Into Various Genres",
        description = "Whether you love action, strategy, or puzzle games, GameHub has something for everyone. Explore an array of game genres and find the perfect game that suits your style. There’s always something new to try!",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Share Your Thoughts",
        description = "Played a game? Leave a rating and review to help other players make informed choices. Your feedback helps us bring you the best gaming experience while keeping the community engaged and informed.",
        image = R.drawable.onboarding3
    )
)

