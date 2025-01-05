package com.vkasurinen.gamestore2

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vkasurinen.gamestore2.domain.repository.GameListRepository
import com.vkasurinen.gamestore2.presentation.details.DetailsScreen
import com.vkasurinen.gamestore2.presentation.details.DetailsScreenRoot
import com.vkasurinen.gamestore2.presentation.gamesbygenre.GamesByGenreScreenRoot
import com.vkasurinen.gamestore2.ui.theme.GameStore2Theme
import com.vkasurinen.gamestore2.util.Resource
import com.vkasurinen.gamestore2.util.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val gameListRepository: GameListRepository by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GameStore2Theme {
                SetBarColor(color = MaterialTheme.colorScheme.inverseOnSurface)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Main.route
                    ) {
                        composable(Screen.Main.route) {
                            MainScreen(navController)
                        }
                        composable(
                            Screen.Details.route + "/{gameId}",
                            arguments = listOf(
                                navArgument("gameId") { type = NavType.IntType }
                            )
                        ) { backStackEntry ->
                            DetailsScreenRoot()
                        }
                        composable(
                            Screen.GamesByGenre.route + "/{genre}",
                            arguments = listOf(
                                navArgument("genre") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val genre = backStackEntry.arguments?.getString("genre") ?: ""
                            GamesByGenreScreenRoot(navController, genre)
                        }
                    }
                }
            }
        }
        testGameListRepository()
    }

    private fun testGameListRepository() {
        CoroutineScope(Dispatchers.IO).launch {
            gameListRepository.getGamesByGenre("action", 10).collect { resource ->
                when (resource) {
                    is Resource.Loading<*> -> {
                        Log.d("MainActivity", "Loading data...")
                    }
                    is Resource.Success<*> -> {
                        Log.d("MainActivity", "Data loaded successfully: ${resource.data}")
                    }
                    is Resource.Error<*> -> {
                        Log.e("MainActivity", "Error loading data: ${resource.message}")
                    }
                    else -> {
                        Log.e("MainActivity", "Unknown resource state")
                    }
                }
            }
        }
    }

    @Composable
    private fun SetBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        LaunchedEffect(key1 = color) {
            systemUiController.setSystemBarsColor(color)
        }
    }
}