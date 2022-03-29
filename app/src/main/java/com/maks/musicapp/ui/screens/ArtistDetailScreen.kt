package com.maks.musicapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.maks.musicapp.R
import com.maks.musicapp.data.music.artist.ArtistResult
import com.maks.musicapp.ui.composeutils.CustomOutlinedButton
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun ArtistDetailScreen(artistResult: ArtistResult) {
    Surface(
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.padding(8.dp)
    ) {
        Column(
            Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState())
        ) {
            GlideImage(
                imageModel = artistResult.image,
                contentScale = ContentScale.Crop,
                circularReveal = CircularReveal(),
                placeHolder = ImageBitmap.imageResource(R.drawable.music_logo)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = artistResult.name,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold
                )
                UrlText(url = artistResult.website)
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomOutlinedButton(text = "Show Tracks", onClick = {

                })
                CustomOutlinedButton(text = "Show Albums", onClick = {

                })
            }
        }
    }
}

@Composable
private fun UrlText(url: String) {
    val value = "Website"
    val annotatedString = buildAnnotatedString(value, url)
    val uriHandler = LocalUriHandler.current
    Text(text = annotatedString, modifier = Modifier.clickable {
        annotatedString
            .getStringAnnotations("URL", 0, value.length - 1)
            .firstOrNull()?.let { stringAnnotation ->
                uriHandler.openUri(stringAnnotation.item)
            }
    })
}

@Composable
private fun buildAnnotatedString(
    value: String,
    url: String
) = buildAnnotatedString {
    addStyle(
        style = SpanStyle(
            color = Color(0xFF2097F7),
            textDecoration = TextDecoration.Underline
        ), 0, value.length - 1

    )
    addStringAnnotation(
        tag = "URL",
        annotation = url,
        0, value.length - 1
    )
}
