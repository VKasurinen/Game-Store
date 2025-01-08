package com.vkasurinen.gamestore2.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.vkasurinen.gamestore2.domain.model.Game
import com.vkasurinen.gamestore2.util.Screen
import com.vkasurinen.gamestore2.util.calculateAverageColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun GameItem(
    game: Game,
    navHostController: NavHostController
) {
    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    var dominantColor by remember { mutableStateOf(defaultColor) }

    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(game.background_image)
            .size(Size.ORIGINAL)
            .build()
    )

    val imageState = painter.state

    LaunchedEffect(imageState) {
        if (imageState is AsyncImagePainter.State.Success) {
            withContext(Dispatchers.IO) {
                val bitmap = imageState.result.drawable.toBitmap()
                val averageColor = calculateAverageColor(bitmap)
                withContext(Dispatchers.Main) {
                    dominantColor = averageColor
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(28.dp))
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.secondaryContainer,
                        dominantColor
                    )
                )
            )
            .clickable {
                navHostController.navigate(Screen.Details.route + "/${game.id}")
            }
    ) {
        if (imageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
                    .width(250.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = null
                )
            }
        } else {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
                    .width(250.dp)
                    .clip(RoundedCornerShape(22.dp)),
                painter = painter,
                contentDescription = game.name,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
            text = game.name,
            color = Color.White,
            fontSize = 15.sp,
            maxLines = 1
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 12.dp, top = 4.dp)
        ) {
            // Add any additional UI elements here, such as rating bars or other information
        }
    }
}

@Preview
@Composable
fun GameItemPreview() {
    val game = Game(
        id = 3498,
        slug = "grand-theft-auto-V",
        name = "Grand Theft Auto V",
        released = "2013-09-17",
        tba = false,
        background_image = "https://media.rawg.io/media/games/20a/20aa03a10cda45239fe22d035c0ebe64.jpg",
        rating = 4.47,
        ratingTop = 5,
        ratingsCount = 6950,
        reviewsTextCount = 63,
        added = 21436,
        metacritic = 92,
        playtime = 74,
        suggestionsCount = 434,
        updated = "2024-12-12T16:47:50",
        reviewsCount = 7058,
        saturatedColor = "#0f0f0f",
        dominantColor = "#0f0f0f",
        //genres = listOf()
    )
    val navController = rememberNavController()
    GameItem(game = game, navHostController = navController)
}