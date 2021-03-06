package com.maks.musicapp.ui.composeutils

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.maks.musicapp.R

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MusicTextField(textValue: String, isVisible: Boolean, onValueChange: (String) -> Unit) {

    val textFieldDescription = stringResource(R.string.music_text_field_description)

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
        exit = fadeOut(tween(durationMillis = 1000))
    ) {
        CustomTextField(
            modifier = Modifier.padding(8.dp),
            value = textValue,
            leadingIcon = Icons.Filled.Search,
            onValueChange = onValueChange,
            label = "Enter song name or artist...",
            textFieldDescription = textFieldDescription
        )
    }
}

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    leadingIcon: ImageVector,
    onValueChange: (String) -> Unit,
    label: String,
    textFieldDescription: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .semantics {
                contentDescription = textFieldDescription
            }
            .fillMaxWidth()
            .clip(CircleShape)
            .border(
                1.dp,
                Color.Black, CircleShape
            ),
        maxLines = 1,
        leadingIcon = {
            Icon(leadingIcon, "")
        },
        label = {
            Text(label)
        },
        isError = isError,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions
    )
}