package com.vkasurinen.gamestore2.di

import androidx.room.Room
import com.vkasurinen.gamestore2.data.local.GameDatabase
import com.vkasurinen.gamestore2.data.remote.GameApi
import com.vkasurinen.gamestore2.data.repository.GameListRepositoryImpl
import com.vkasurinen.gamestore2.domain.repository.GameListRepository
import com.vkasurinen.gamestore2.presentation.details.DetailsViewModel
import com.vkasurinen.gamestore2.presentation.gamelist.GameListViewModel
import com.vkasurinen.gamestore2.presentation.genrelist.GenreListViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GameApi.BASE_URL)
            .client(get())
            .build()
            .create(GameApi::class.java)
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            GameDatabase::class.java,
            "gamedb.db"
        ).build()
    }

    single<GameListRepository> { GameListRepositoryImpl(get(), get()) }

    viewModel { GameListViewModel(get()) }
    viewModel { GenreListViewModel(get()) }
    viewModel { DetailsViewModel(get(), get()) }
}