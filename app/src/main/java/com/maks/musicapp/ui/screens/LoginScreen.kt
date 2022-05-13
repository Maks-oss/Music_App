package com.maks.musicapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.maks.musicapp.R
import com.maks.musicapp.ui.composeutils.CustomTextField
import com.maks.musicapp.ui.viewmodels.LoginViewModel

@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {
    val loginViewModelStates = loginViewModel.loginViewModelStates
    Surface(
        modifier = Modifier.padding(8.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.music_logo),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(200.dp)
            )
            CustomTextField(
                value = loginViewModelStates.login,
                leadingIcon = Icons.Filled.Person,
                onValueChange = { loginViewModel.applyLogin(it) },
                label = "Enter login",
            )
            CustomTextField(
                value = loginViewModelStates.password,
                leadingIcon = Icons.Filled.Password,
                onValueChange = { loginViewModel.applyPassword(it) },
                label = "Enter password",
            )
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = { /*TODO*/ }) {
                    Text("Sign in")
                }
                Button(onClick = { /*TODO*/ }) {
                    Text("Sign up")
                }
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.google_logo), contentDescription = "Sign in with google")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(painter = painterResource(id = R.drawable.facebook_logo), contentDescription = "Sign in with facebook")
                }
            }

        }
    }

}