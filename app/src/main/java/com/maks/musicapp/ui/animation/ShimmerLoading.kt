package com.maks.musicapp.ui.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.maks.musicapp.R

@Composable
private fun ShimmerCard(brush: Brush) {
    Column(modifier = Modifier.padding(8.dp)) {
        val shimmerAnimationItemDescription = stringResource(R.string.shimmer_animation_item)
        Surface(
            shape = MaterialTheme.shapes.medium,
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = shimmerAnimationItemDescription }
                    .height(200.dp)
                    .background(brush = brush)
            )
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun ShimmerGridContent() {
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        repeat(50) {
            item {
                ShimmerAnimation()
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
private fun ShimmerColumnContent() {
    LazyColumn {
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
fun DisplayShimmer(isLoading: Boolean, isVertical: Boolean = true) {
    if (isLoading) {
        if (isVertical) {
            ShimmerGridContent()
        } else {
            ShimmerColumnContent()
        }
    }
}
