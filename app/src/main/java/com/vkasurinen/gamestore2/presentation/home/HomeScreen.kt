package com.vkasurinen.gamestore2.presentation.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.tooling.preview.Preview
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vkasurinen.gamestore2.R
import com.vkasurinen.gamestore2.domain.model.Game
import com.vkasurinen.gamestore2.presentation.components.GameItem
import com.vkasurinen.gamestore2.presentation.components.HomeScreenCard
import org.koin.androidx.compose.koinViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreenRoot(
    navController: NavHostController,
    viewModel: HomeViewModel = koinViewModel(),
) {
    HomeScreen(
        state = viewModel.homeState.value,
        onAction = viewModel::onEvent,
        navHostController = navController
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onAction: (HomeUiEvent) -> Unit,
    navHostController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GameCategories()
        FeaturedGameSection(state.featuredGames, navHostController)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GameCategories() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            listOf("Action", "Adventure", "Shooter", "Family", "Racing").forEach { category ->
                Text(
                    text = category,
                    color = Color.Black,
                    modifier = Modifier
                        .background(
                            if (category == "Action") Color(0xFF00C853) else Color.Transparent,
                            RoundedCornerShape(16.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color(0xFF102A21),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
fun FeaturedGameSection(games: List<Game>, navHostController: NavHostController) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(text = "Our selection", color = Color.Black, style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(games) { game ->
                HomeScreenCard(game = game, navController = navHostController)
            }
        }
    }
}


@Composable
fun TopGamesList(games: List<Game>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Top racing games", color = Color.White, style = MaterialTheme.typography.titleLarge)
            Text(text = "See All", color = Color(0xFF00C853))
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(games) { game ->
                GameItem(game = game, navHostController = rememberNavController())
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeState(),
        onAction = {},
        navHostController = rememberNavController()
    )
}