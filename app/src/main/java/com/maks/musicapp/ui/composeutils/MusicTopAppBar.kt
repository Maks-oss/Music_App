package com.maks.musicapp.ui.composeutils

import androidx.compose.foundation.clickable
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.maks.musicapp.R

@Composable
fun MusicTopAppBar(navigationIconClick: () -> Unit) {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        navigationIcon = {
            Icon(Icons.Filled.Menu, "", modifier = Modifier.clickable(onClick = navigationIconClick))
        }
    )
}