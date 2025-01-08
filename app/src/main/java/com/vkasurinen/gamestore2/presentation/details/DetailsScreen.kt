package com.vkasurinen.gamestore2.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Category
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.vkasurinen.gamestore2.R
import com.vkasurinen.gamestore2.domain.model.Game
import com.vkasurinen.gamestore2.presentation.components.GameItem
import com.vkasurinen.gamestore2.presentation.components.UtilityBox
import com.vkasurinen.gamestore2.util.RatingBar
import org.koin.androidx.compose.koinViewModel

@Composable
fun DetailsScreenRoot(
    viewModel: DetailsViewModel = koinViewModel(),
) {
    DetailsScreen(
        state = viewModel.detailsState.collectAsState().value,
    )
}


@Composable
fun DetailsScreen(
    state: DetailsState,
    //onAction: (DetailsUiEvent) -> Unit
) {
    val backDropImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(state.game?.background_image)
            .size(Size.ORIGINAL)
            .build()
    ).state

    val posterImageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(state.game?.background_image)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (backDropImageState is AsyncImagePainter.State.Error) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = state.game?.name
                )
            }
        }

        if (backDropImageState is AsyncImagePainter.State.Success) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {

                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .clip(
                            RoundedCornerShape(0.dp).copy(
                                bottomEnd = CornerSize(20.dp),
                                bottomStart = CornerSize(20.dp)
                            )
                        ),
                    painter = backDropImageState.painter,
                    contentDescription = state.game?.name,
                    contentScale = ContentScale.Crop,

                    )

                state.game?.let { game ->
                    Box(
                        modifier = Modifier
                            .widthIn(min = 200.dp)
                            .heightIn(50.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 8.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            modifier = Modifier,
                            text = game.name,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Green
                        )
                    }
                }


            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
            //.padding(16.dp)
        ) {

            state.game?.let { game ->
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {


                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        UtilityBox(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Rounded.Star,
                            title = "Rating",
                            value = "${state.game?.rating ?: 0.0} stars"
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        UtilityBox(
                            modifier = Modifier.weight(1f),
                            icon = Icons.Rounded.Category,
                            title = "Genre",
                            value = "sad"
                            //value = state.game?.genres?.joinToString(", ") { it.name } ?: "No genres available"
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
                        modifier = Modifier
                            .padding(horizontal = 30.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Install",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "About",
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = stringResource(R.string.release_date) + " " + game.released
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp)
                        ) {

                            RatingBar(
                                starsModifier = Modifier.size(18.dp),
                                rating = game.rating / 2
                            )

                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = game.rating.toString().take(3),
                                color = MaterialTheme.colorScheme.onBackground,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                maxLines = 1,
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Description here",
                            fontSize = 15.sp,
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))


    }
}


//@Composable
//fun DetailsScreen(
//    state: DetailsState,
//    //onAction: (DetailsUiEvent) -> Unit
//) {
//    val backDropImageState = rememberAsyncImagePainter(
//        model = ImageRequest.Builder(LocalContext.current)
//            .data(state.game?.background_image)
//            .size(Size.ORIGINAL)
//            .build()
//    ).state
//
//    val posterImageState = rememberAsyncImagePainter(
//        model = ImageRequest.Builder(LocalContext.current)
//            .data(state.game?.background_image)
//            .size(Size.ORIGINAL)
//            .build()
//    ).state
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//    ) {
//        if (backDropImageState is AsyncImagePainter.State.Error) {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(220.dp)
//                    .background(MaterialTheme.colorScheme.primaryContainer),
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    modifier = Modifier.size(70.dp),
//                    imageVector = Icons.Rounded.ImageNotSupported,
//                    contentDescription = state.game?.name
//                )
//            }
//        }
//
//        if (backDropImageState is AsyncImagePainter.State.Success) {
//            Image(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(220.dp)
//                    .clip(
//                        RoundedCornerShape(0.dp).copy(
//                            bottomEnd = CornerSize(20.dp),
//                            bottomStart = CornerSize(20.dp)
//                        )
//                    ),
//                painter = backDropImageState.painter,
//                contentDescription = state.game?.name,
//                contentScale = ContentScale.Crop,
//
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Box(
//                modifier = Modifier
//                    .width(160.dp)
//                    .height(240.dp)
//            ) {
//                if (posterImageState is AsyncImagePainter.State.Error) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .clip(RoundedCornerShape(12.dp))
//                            .background(MaterialTheme.colorScheme.primaryContainer),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            modifier = Modifier.size(70.dp),
//                            imageVector = Icons.Rounded.ImageNotSupported,
//                            contentDescription = state.game?.name
//                        )
//                    }
//                }
//
//                if (posterImageState is AsyncImagePainter.State.Success) {
//                    Image(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .clip(RoundedCornerShape(12.dp)),
//                        painter = posterImageState.painter,
//                        contentDescription = state.game?.name,
//                        contentScale = ContentScale.Crop
//                    )
//                }
//            }
//
//            state.game?.let { game ->
//                Column(
//                    modifier = Modifier.fillMaxWidth()
//                ) {
//                    Text(
//                        modifier = Modifier.padding(start = 16.dp),
//                        text = game.name,
//                        fontSize = 19.sp,
//                        fontWeight = FontWeight.SemiBold
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Row(
//                        modifier = Modifier
//                            .padding(start = 16.dp)
//                    ) {
//
//                        RatingBar(
//                            starsModifier = Modifier.size(18.dp),
//                            rating = game.rating / 2
//                        )
//
//                        Text(
//                            modifier = Modifier.padding(start = 4.dp),
//                            text = game.rating.toString().take(3),
//                            color = MaterialTheme.colorScheme.onBackground,
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.SemiBold,
//                            maxLines = 1,
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.height(12.dp))
//
//                    Text(
//                        modifier = Modifier.padding(start = 16.dp),
//                        text = stringResource(R.string.release_date) + game.released
//                    )
//
//                    Spacer(modifier = Modifier.height(10.dp))
//
//                    Text(
//                        modifier = Modifier.padding(start = 16.dp),
//                        text = "${game.reviewsCount} " + stringResource(R.string.votes)
//                    )
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//
//        Text(
//            modifier = Modifier.padding(start = 16.dp),
//            text = stringResource(R.string.overview),
//            fontSize = 19.sp,
//            fontWeight = FontWeight.SemiBold
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        state.game?.let {
//            Text(
//                modifier = Modifier.padding(start = 16.dp),
//                text = it.updated,
//                fontSize = 16.sp,
//            )
//        }
//
//        Spacer(modifier = Modifier.height(32.dp))
//    }
//}





