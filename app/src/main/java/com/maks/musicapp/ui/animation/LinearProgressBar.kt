package com.maks.musicapp.ui.animation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar(isLoading:Boolean){
    if (isLoading) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(8.dp))
    }
}