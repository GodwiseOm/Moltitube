package com.example.moltitube.Screens

import android.content.Context
import android.graphics.ColorFilter
import android.widget.EditText
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues


import androidx.compose.foundation.layout.fillMaxHeight

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.error
import coil3.request.placeholder
import com.example.compose.MoltiTubeTheme
import com.example.moltitube.Data.MockData
import com.example.moltitube.Model.Item
import com.example.moltitube.Model.VideoItem
import com.example.moltitube.R
import kotlinx.coroutines.delay


import kotlin.math.abs
import kotlin.math.sin


@Composable
fun TubeDisplay(modifier: Modifier = Modifier) {


    Box(modifier = modifier.fillMaxSize()) {

        var visibility  by rememberSaveable {  mutableStateOf(true)}
        WelcomeScreen(visibility = visibility, modifier = Modifier.zIndex(8f))
        LaunchedEffect(Unit) {
            delay(7000)
            visibility = false
        }

        TubeBox(modifier = Modifier.align(Alignment.TopStart), 0)
        TubeBox(modifier = Modifier.align(Alignment.TopEnd), 1)
        TubeBox(modifier = Modifier.align(Alignment.BottomStart), 2)
        TubeBox(modifier = Modifier.align(Alignment.BottomEnd), 3)


    }

}

@Composable
fun TubeBox(modifier: Modifier = Modifier, viewModelKey: Int) {
    val viewModel: DisplayScreenViewModel = viewModel(key = viewModelKey.toString())
    var showSearchList by viewModel.showSearchList
    var showPlayingVideo: MutableState<Boolean> = viewModel.showPlayingVideo
    var sliderLevel by remember { mutableFloatStateOf(1f) }
    val playingVideo by viewModel.playingVideo

    Box(
        modifier = modifier
            .fillMaxSize(2 / 4f)
            .padding(12.dp)
            .background(color = Color(MaterialTheme.colorScheme.surfaceContainer.value))
    ) {
        if (showPlayingVideo.value) {
            Image(modifier = Modifier
                .zIndex(3f)
                .align(Alignment.TopEnd)
                .clickable { viewModel.cancelPlayingVideo() },
                painter = painterResource(R.drawable.baseline_cancel_24),
                contentDescription = "cancel video"
            )
            YoutubeScreen(playingVideo, modifier = Modifier.zIndex(2f))
        }
        Card(
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(bottom = 12.dp)
                .fillMaxWidth()
                .fillMaxHeight(sliderLevel)
                .zIndex(1f)
                .pointerInput(Unit) {
                    detectVerticalDragGestures { change, dragAmount ->
                        val absoluteDrag = abs(dragAmount)
                        val dragSign = dragAmount / absoluteDrag
                        sliderLevel =
                            sliderLevel
                                .plus(dragSign * 0.023)
                                .toFloat()
                                .coerceIn(0.05f, 1f)
                        change.consume()


                    }

                }

                .shadow(elevation = 12.dp)


        ) {

        }


        if (sliderLevel <= 0.05f) {
            if (showSearchList) {

                SearchAndResult(
                    searchmodifier = Modifier.align(Alignment.TopCenter),
                    viewModel = viewModel
                )
            }


        }
    }
}


@Composable
fun SearchAndResult(
    searchmodifier: Modifier = Modifier,
    resultModifier: Modifier = Modifier,
    viewModel: DisplayScreenViewModel

) {


    var text by rememberSaveable { mutableStateOf("") }
    Column() {
        OutlinedTextField(
            shape = RoundedCornerShape(0.dp),
            singleLine = true,
            trailingIcon = {
                Image(modifier = Modifier.padding(top = 4.dp),

                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = null
                )
            },
            modifier = searchmodifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                text = it

            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                if (text.isNotEmpty()) {
                    viewModel.searchYoutube(text)
                }
            }
            ))
        VideoList(
            modifier = resultModifier,
            context = LocalContext.current,
            videoItems = viewModel.searchResults.collectAsState().value,
            onImageClicked = { viewModel.imageClicked(it) }
        )


    }
}

@Composable
fun VideoItemView(
    item: Item,
    context: Context,
    modifier: Modifier = Modifier,
    onImageClick: (Item) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(0.5f)
            .padding(bottom = 20.dp)
    ) {
        AsyncImage(
            modifier = Modifier.clickable { onImageClick(item) },
            contentScale = ContentScale.Fit,
            model = ImageRequest.Builder(context).data(item.snippet.thumbnails.high.url)
                .placeholder(R.drawable.baseline_airplay_480)
                .error(R.drawable.c_ronaldo).build(),
            contentDescription = null

        )
        Text(
            style = MaterialTheme.typography.titleSmall,
            text = item.snippet.title,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            fontSize = 12.sp
        )
    }

}

@Composable
fun VideoList(
    modifier: Modifier = Modifier,
    videoItems: List<Item>,
    context: Context,
    onImageClicked: (Item) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        items(videoItems) { videoItem ->
            VideoItemView(
                item = videoItem,

                context = context, onImageClick = onImageClicked
            )
        }

    }
}

@Preview
@Composable
fun SearchPreview() {
    MoltiTubeTheme {
        SearchAndResult(viewModel = viewModel())
    }
}

@Preview
@Composable
fun TubePreview() {
    MoltiTubeTheme {
        val data = MockData().item
        VideoItemView(

            context = LocalContext.current,
            item = data,
            onImageClick = {})

    }
}