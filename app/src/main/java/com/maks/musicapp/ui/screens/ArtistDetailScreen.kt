package com.maks.musicapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import com.maks.musicapp.utils.AppConstants
import com.maks.musicapp.utils.Routes
import com.maks.musicapp.ui.viewmodels.MusicViewModel
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage

@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun ArtistDetailScreen(
    musicViewModel: MusicViewModel,
    navController: NavController,
    snackbarHostState: SnackbarHostState,
    artistResult: ArtistResult,
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val artistTracks = musicViewModel.artistTracksUiState.tracksResult
    var isButtonClicked by remember { mutableStateOf(false) }
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
            isButtonClicked = true
        })

    }
//    ProcessArtistTracksState(
//        artistTracks,
//        bottomSheetState,
//        snackbarHostState,
//        isButtonClicked,
//        musicViewModel.musicViewModelStates
//    )


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
                .verticalScroll(rememberScrollState())
        ) {
            GlideImage(
                imageModel = artistResult.image?.ifEmpty {
                    AppConstants.DEFAULT_IMAGE
                },
                contentScale = ContentScale.Crop,
                circularReveal = CircularReveal(),
                placeHolder = ImageBitmap.imageResource(R.drawable.music_logo)
            )
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = artistResult.name,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
                if (artistResult.website.isNotEmpty()) {
                    UrlText(url = artistResult.website)
                }
            }
            Spacer(modifier = Modifier.padding(8.dp))
            CustomOutlinedButton(
                modifier = Modifier.padding(8.dp),
                text = "Show Album Tracks",
                onClick = showTracksAction
            )

        }
    }
}

@Composable
private fun UrlText(url: String) {

    val annotatedString = buildAnnotatedString("Website", url)
    val uriHandler = LocalUriHandler.current
    Text(text = annotatedString, modifier = Modifier.clickable {
        uriHandler.openUri(url)
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
        ), 0, value.length
    )

    addStringAnnotation(
        tag = "URL",
        annotation = url,
        0, value.length
    )
    append(value)
}
