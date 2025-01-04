package com.vkasurinen.gamestore2.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromGameEntityList(value: List<GameEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<GameEntity>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toGameEntityList(value: String): List<GameEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<GameEntity>>() {}.type
        return gson.fromJson(value, type)
    }
}