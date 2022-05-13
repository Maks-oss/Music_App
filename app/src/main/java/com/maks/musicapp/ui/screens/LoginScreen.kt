package com.maks.musicapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.maks.musicapp.R
import com.maks.musicapp.ui.composeutils.CustomTextField
import com.maks.musicapp.ui.viewmodels.LoginViewModel
import com.maks.musicapp.utils.Routes

@Composable
fun LoginScreen(loginViewModel: LoginViewModel, navController: NavController) {
    val loginViewModelStates = loginViewModel.loginViewModelStates
    Surface(
        modifier = Modifier.padding(8.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.music_backgorund),
                contentDescription = "",
                contentScale = ContentScale.Fit,
            )
            Spacer(modifier = Modifier.padding(8.dp))
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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = { navController.navigate(Routes.MainGraphRoute.route) }) {
                    Text("Sign in")
                }
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedButton(onClick = { navController.navigate(Routes.MainGraphRoute.route) }) {
                    Text("Sign up")
                }
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Surface(elevation = 8.dp, shape = MaterialTheme.shapes.small) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.google_logo),
                            tint = Color.Unspecified,
                            contentDescription = "Sign in with google",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Surface(elevation = 8.dp, shape = MaterialTheme.shapes.small) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.facebook_logo),
                            tint = Color.Unspecified,
                            contentDescription = "Sign in with facebook",
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            }

        }
    }

}