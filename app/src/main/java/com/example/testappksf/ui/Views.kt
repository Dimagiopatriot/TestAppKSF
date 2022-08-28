package com.example.testappksf.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.testappksf.R
import com.example.testappksf.domain.PLACE_STUB
import com.example.testappksf.domain.data.Place
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.flow.distinctUntilChanged


@Composable
@Preview
fun PlaceItemCard(place: Place = PLACE_STUB) {
    Card(
        shape = MaterialTheme.shapes.medium,
        elevation = 5.dp,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    colorResource(id = R.color.white)
                )
                .padding(8.dp),
        ) {
            Text(
                text = place.placeName,
                color = Color.Black,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = place.address,
                color = Color.Gray,
                fontSize = 22.sp,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.padding(top = 16.dp)
            )
            place.webSite?.let {
                val context = LocalContext.current
                Text(
                    text = place.webSite,
                    color = Color.Blue,
                    fontSize = 22.sp,
                    style = TextStyle(textDecoration = TextDecoration.Underline),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(place.webSite))
                            startActivity(context, browserIntent, null)
                        }
                )
            }
            place.phoneNumber?.let {
                val context = LocalContext.current
                Text(
                    text = place.phoneNumber,
                    color = Color.Black,
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable {
                            val dial = Intent(Intent.ACTION_DIAL)
                            dial.data = Uri.parse("tel:${place.phoneNumber}")
                            startActivity(context, dial, null)
                        }
                )
            }
            place.openingHours?.let {
                Text(
                    text = place.openingHours,
                    color = Color.Gray,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun LazyListState.OnBottomReached(
    buffer: Int = 0,
    onLoadMore: () -> Unit
) {
    require(buffer >= 0) { "buffer cannot be negative, but was $buffer" }

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false

            lastVisibleItem.index >= layoutInfo.totalItemsCount - 1 - buffer
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .collect { if (it) onLoadMore() }
    }
}

@Composable
@Preview
fun ErrorPlaceMessage(errorText: String = "No data", isVisible: Boolean = true) {
    if (isVisible) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = errorText,
                color = Color.Gray,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 8.dp),
                fontWeight = FontWeight.Bold,
            )
        }
    }
}

@Composable
fun LoadingAnimation(
    indicatorSize: Dp = 100.dp,
    circleColors: List<Color> = listOf(
        Color(0xFF5851D8),
        Color(0xFF833AB4),
        Color(0xFFC13584),
        Color(0xFFE1306C)
    ),
    animationDuration: Int = 360,
    isVisible: Boolean,
) {
    if (isVisible) {
        val infiniteTransition = rememberInfiniteTransition()

        val rotateAnimation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = animationDuration,
                    easing = LinearEasing
                )
            )
        )

        CircularProgressIndicator(
            modifier = Modifier
                .size(size = indicatorSize)
                .rotate(degrees = rotateAnimation)
                .border(
                    width = 4.dp,
                    brush = Brush.sweepGradient(circleColors),
                    shape = CircleShape
                ),
            progress = 1f,
            strokeWidth = 1.dp,
            color = MaterialTheme.colors.background // Set background color
        )
    }
}

@Composable
fun PlacesList(
    places: List<Place> = listOf(PLACE_STUB, PLACE_STUB, PLACE_STUB),
    listState: LazyListState,
    isRefreshing: Boolean = false,
    onRefresh: () -> Unit = {}
) {

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = onRefresh,
    ) {
        LazyColumn(state = listState) {
            items(places) { place ->
                PlaceItemCard(place)
            }
        }
    }
}