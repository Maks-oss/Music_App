package com.maks.musicapp.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import com.maks.musicapp.R
import com.maks.musicapp.data.music.artist.ArtistResult
import com.maks.musicapp.ui.composeutils.CustomOutlinedButton
import com.maks.musicapp.ui.composeutils.TrackBottomSheetLayout
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.viewmodels.MusicViewModel
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ArtistDetailScreen(
    musicViewModel: MusicViewModel,
    navController: NavController,
    artistResult: ArtistResult,
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val coroutineScope = rememberCoroutineScope()
    TrackBottomSheetLayout(
        musicViewModel = musicViewModel,
        bottomSheetState = bottomSheetState,
        trackListItemClickAction = { trackResult ->
            musicViewModel.currentTrack = trackResult
            navController.navigate(Routes.TrackDetailsScreenRoute.route)
        }
    ) {
        DisplayArtistDetail(artistResult, showTracksAction = {
            musicViewModel.findArtistsTracks()
            coroutineScope.launch {
                bottomSheetState.show()
            }
        })
    }

}

@Composable
private fun DisplayArtistDetail(artistResult: ArtistResult, showTracksAction: () -> Unit) {
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

            CustomOutlinedButton(text = "Show Album Tracks", onClick = showTracksAction)

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
