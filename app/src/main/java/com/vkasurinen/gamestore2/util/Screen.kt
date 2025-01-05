package com.vkasurinen.gamestore2.util

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object GameList : Screen("gameList")
    object GenreList : Screen("genreList")

    object GamesByGenre : Screen("gamesByGenre")
    object Details : Screen("details")
}