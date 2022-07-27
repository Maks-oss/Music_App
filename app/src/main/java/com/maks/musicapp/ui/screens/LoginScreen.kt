package com.maks.musicapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.maks.musicapp.R
import com.maks.musicapp.ui.composeutils.CustomTextField
import com.maks.musicapp.ui.viewmodels.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    googleSignIn: () -> Unit,
    inAppSignIn: (email: String, password: String) -> Unit,
    inAppSignUp: (email: String, password: String) -> Unit,
) {
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
                value = loginViewModelStates.email,
                leadingIcon = Icons.Filled.Person,
                onValueChange = { loginViewModel.applyEmail(it) },
                label = "Enter email",
                isError = loginViewModelStates.isEmailError,
            )
            if (loginViewModelStates.isEmailError) {
                Text(
                    text = stringResource(R.string.email_error_message),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            CustomTextField(
                value = loginViewModelStates.password,
                leadingIcon = Icons.Filled.Password,
                onValueChange = { loginViewModel.applyPassword(it) },
                label = "Enter password",
                isError = loginViewModelStates.isPasswordError,
                trailingIcon = {
                    IconButton(onClick = { loginViewModel.applyPasswordVisibility(!loginViewModelStates.isPasswordHidden) }) {
                        val visibilityIcon =
                            if (loginViewModelStates.isPasswordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                        val description =
                            if (loginViewModelStates.isPasswordHidden) "Show password" else "Hide password"
                        Icon(imageVector = visibilityIcon, contentDescription = description)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (loginViewModelStates.isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            )
            if (loginViewModelStates.isPasswordError) {
                Text(
                    text = stringResource(R.string.password_error_message),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    inAppSignIn(loginViewModelStates.email, loginViewModelStates.password)
                }) {
                    Text("Sign in")
                }
                Spacer(modifier = Modifier.padding(8.dp))
                OutlinedButton(onClick = {
                    inAppSignUp(loginViewModelStates.email, loginViewModelStates.password)
                }) {
                    Text("Sign up")
                }
            }
            ExtendedFloatingActionButton(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp),
                text = { Text("Sign in with Google") },
                onClick = googleSignIn,
                backgroundColor = MaterialTheme.colors.background,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.google_logo),
                        tint = Color.Unspecified,
                        contentDescription = "Sign in with google",
                        modifier = Modifier.size(30.dp)
                    )
                })

        }
    }

}




