package com.vkasurinen.gamestore2.data.remote.response

data class GameListDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<GameDto>
)