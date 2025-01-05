package com.vkasurinen.gamestore2.presentation.components

import android.graphics.Bitmap
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.vkasurinen.gamestore2.domain.model.Game
import com.vkasurinen.gamestore2.domain.model.Genre
import com.vkasurinen.gamestore2.util.Screen
import com.vkasurinen.gamestore2.util.getAverageColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import resizeBitmapFromUrl
import java.net.URL

@Composable
fun GenreItem(
    genre: Genre,
    navHostController: NavHostController
) {
    var resizedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val defaultColor = MaterialTheme.colorScheme.secondaryContainer
    var dominantColor by remember { mutableStateOf(defaultColor) }

    LaunchedEffect(genre.image_background) {
        if (!genre.image_background.isNullOrEmpty()) {
            withContext(Dispatchers.IO) {
                resizedBitmap = resizeBitmapFromUrl(genre.image_background, 200, 250)
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
                navHostController.navigate(Screen.Details.route + "/${genre.id}")
            }
    ) {
        if (resizedBitmap == null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
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
            dominantColor = getAverageColor(
                imageBitmap = resizedBitmap!!.asImageBitmap()
            )

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(22.dp)),
                bitmap = resizedBitmap!!.asImageBitmap(),
                contentDescription = genre.name,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
            text = genre.name,
            color = Color.White,
            fontSize = 15.sp,
            maxLines = 1
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, bottom = 12.dp, top = 4.dp)
        ) {
            // Here more items?
        }
    }
}

@Preview
@Composable
fun GenreItemPreview() {
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
        dominantColor = "#0f0f0f"
    )
    val navController = rememberNavController()
    GameItem(game = game, navHostController = navController)
}