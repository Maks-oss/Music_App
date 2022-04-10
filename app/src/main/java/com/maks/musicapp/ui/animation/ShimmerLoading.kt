package com.maks.musicapp.ui.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
private fun ShimmerCard(brush: Brush) {
    Column(modifier = Modifier.padding(8.dp)) {
        Surface(
            shape = MaterialTheme.shapes.medium,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(brush = brush)
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun ShimmerContent() {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        repeat(50) {
            item {
                ShimmerAnimation()
            }
        }
    }
}


@Composable
private fun ShimmerAnimation(
) {

    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(

            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )

    val brush = Brush.linearGradient(
        colors = listOf(

            Color.LightGray.copy(0.9f),

            Color.LightGray.copy(0.2f),

            Color.LightGray.copy(0.9f)

        ),
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerCard(brush = brush)

}

@ExperimentalFoundationApi
@Composable
fun DisplayShimmer(isLoading: Boolean) {
    if (isLoading) {
        ShimmerContent()
    }
}
