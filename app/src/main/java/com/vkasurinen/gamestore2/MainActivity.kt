package com.vkasurinen.gamestore2

import MainScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.vkasurinen.gamestore2.domain.repository.GameListRepository
import com.vkasurinen.gamestore2.presentation.details.DetailsScreenRoot
import com.vkasurinen.gamestore2.presentation.gamesbygenre.GamesByGenreScreenRoot
import com.vkasurinen.gamestore2.presentation.onboarding.OnBoardingScreen
import com.vkasurinen.gamestore2.presentation.onboarding.OnBoardingViewModel
import com.vkasurinen.gamestore2.presentation.onboarding.onBoardingEvent
import com.vkasurinen.gamestore2.util.Screen
import com.vkasurinen.gamestore2.ui.theme.GameStore2Theme
import com.vkasurinen.gamestore2.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val gameListRepository: GameListRepository by inject()

    private val onBoardingViewModel: OnBoardingViewModel by viewModel()
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
                        startDestination = Screen.OnBoarding.route
                    ) {

                        composable(Screen.OnBoarding.route) {
                            OnBoardingScreen(
                                onEvent = { event ->
                                    onBoardingViewModel.onEvent(event)
                                    if (event is onBoardingEvent.NavigateToMainScreen) {
                                        navController.navigate(Screen.Main.route) {
                                            popUpTo(Screen.OnBoarding.route) { inclusive = true }
                                        }
                                    }
                                }
                            )
                        }

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