package com.vkasurinen.gamestore2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [GameEntity::class],
    version = 1
)

abstract class GameDatabase: RoomDatabase() {
    abstract val gameDao: GameDao
}