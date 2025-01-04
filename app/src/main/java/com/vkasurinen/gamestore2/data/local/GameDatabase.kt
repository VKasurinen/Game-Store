package com.vkasurinen.gamestore2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [GameEntity::class, GenreEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class GameDatabase: RoomDatabase() {
    abstract val gameDao: GameDao
    abstract val genreDao: GenreDao
}