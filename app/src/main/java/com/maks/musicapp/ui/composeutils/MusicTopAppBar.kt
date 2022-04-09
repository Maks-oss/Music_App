package com.maks.musicapp.ui.composeutils

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.maks.musicapp.R

@Composable
fun MusicTopAppBar() {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
    )
}