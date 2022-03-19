package com.maks.musicapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.maks.musicapp.R

@Composable
fun LoginScreen(navigateAction:()->Unit) {

    Column(modifier = Modifier.fillMaxSize(), Arrangement.SpaceEvenly) {
        Image(
            painter = painterResource(id = R.drawable.music_logo),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Button(onClick = navigateAction, modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Text(text = "Sign in")
        }
    }

}
