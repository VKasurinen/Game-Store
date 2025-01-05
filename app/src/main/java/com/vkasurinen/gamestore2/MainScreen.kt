import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Gamepad
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vkasurinen.gamestore2.presentation.gamelist.GameListUiEvent
import com.vkasurinen.gamestore2.presentation.gamelist.GameListViewModel
import com.vkasurinen.gamestore2.presentation.gamelist.GameScreenRoot
import com.vkasurinen.gamestore2.presentation.genrelist.GenreListViewModel
import com.vkasurinen.gamestore2.presentation.genrelist.GenreScreenRoot
import com.vkasurinen.gamestore2.presentation.gamesbygenre.GamesByGenreScreenRoot
import com.vkasurinen.gamestore2.presentation.home.HomeScreen
import com.vkasurinen.gamestore2.util.Screen
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    val gameListViewModel: GameListViewModel = koinViewModel()
    val genreListViewModel: GenreListViewModel = koinViewModel()
    val gameListState = gameListViewModel.gameListState.collectAsState().value
    val bottomNavController = rememberNavController()

    Scaffold(bottomBar = {
        BottomNavigationBar(
            bottomNavController = bottomNavController, onEvent = gameListViewModel::onEvent
        )
    }, topBar = {
        TopAppBar(
            title = {
                Text(
                    text = when (bottomNavController.currentBackStackEntry?.destination?.route) {
                        Screen.Home.route -> "Home"
                        Screen.GameList.route -> "Games"
                        Screen.GenreList.route -> "Genres"
                        else -> "App"
                    },
                    fontSize = 20.sp
                )
            },
            modifier = Modifier.shadow(2.dp),
            colors = TopAppBarDefaults.topAppBarColors(
                MaterialTheme.colorScheme.inverseOnSurface
            )
        )
    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = bottomNavController,
                startDestination = Screen.Home.route
            ) {
                composable(Screen.Home.route) {
                    HomeScreen()
                }
                composable(Screen.GameList.route) {
                    GameScreenRoot(
                        navController = navController,
                        viewModel = gameListViewModel
                    )
                }
                composable(Screen.GenreList.route) {
                    GenreScreenRoot(
                        navController = navController,
                        viewModel = genreListViewModel
                    )
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

@Composable
fun BottomNavigationBar(
    bottomNavController: NavHostController, onEvent: (GameListUiEvent) -> Unit
) {

    val items = listOf(
        BottomItem(
            "Home",
            icon = Icons.Rounded.Home
        ),
        BottomItem(
            "Games",
            icon = Icons.Rounded.Gamepad
        ), BottomItem(
            "Genres",
            icon = Icons.Rounded.Category
        )
    )

    val selected = rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar {
        Row(modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface)) {
            items.forEachIndexed { index, bottomItem ->
                NavigationBarItem(
                    selected = selected.intValue == index,
                    onClick = {
                        selected.intValue = index
                        when (selected.intValue) {
                            0 -> bottomNavController.navigate(Screen.Home.route)
                            1 -> bottomNavController.navigate(Screen.GameList.route)
                            2 -> bottomNavController.navigate(Screen.GenreList.route)
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = bottomItem.icon,
                            contentDescription = bottomItem.title,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    },
                    label = {
                        Text(
                            text = bottomItem.title,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }
}

data class BottomItem(
    val title: String, val icon: ImageVector
)