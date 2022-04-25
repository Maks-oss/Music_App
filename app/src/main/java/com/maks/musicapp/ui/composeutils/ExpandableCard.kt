package com.maks.musicapp.ui.composeutils

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.maks.musicapp.data.domain.Feed
import com.maks.musicapp.utils.ExpandableCardsConstants

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun ExpandableFeedCard(
    feed: Feed,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState, label = "transition")
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = ExpandableCardsConstants.EXPAND_ANIMATION_DURATION)
    }, label = "rotationDegreeTransition") {
        if (expanded) 0f else 180f
    }
    Card(
        modifier = Modifier
            .padding(8.dp), elevation = 8.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = feed.title,
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.9f)
                        .horizontalScroll(rememberScrollState())
                        .align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.h6,
                    maxLines = 1
                )
                CardArrow(
                    degrees = arrowRotationDegree,
                    onClick = onCardArrowClick,
                )
            }
            ExpandableContent(visible = expanded) {
                Spacer(modifier = Modifier.padding(8.dp))
                Text(text = feed.text, style = MaterialTheme.typography.body1)
            }
        }
    }
}

@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                imageVector = Icons.Filled.ExpandMore,
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),
            )
        },
        modifier = Modifier.wrapContentWidth()
    )
}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ExpandableContent(
    visible: Boolean = true,
    content: @Composable () -> Unit
) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = ExpandableCardsConstants.FADE_IN_ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(ExpandableCardsConstants.EXPAND_ANIMATION_DURATION))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = ExpandableCardsConstants.FADE_OUT_ANIMATION_DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(ExpandableCardsConstants.COLLAPSE_ANIMATION_DURATION))
    }
    AnimatedVisibility(
        visible = visible,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        content()
    }
}