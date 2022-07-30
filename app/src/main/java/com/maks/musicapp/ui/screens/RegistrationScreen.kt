package com.maks.musicapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.maks.musicapp.R
import com.maks.musicapp.ui.composeutils.CustomTextField
import com.maks.musicapp.ui.viewmodels.RegistrationViewModel

@Composable
fun RegistrationScreen(
    registrationViewModel: RegistrationViewModel,
    selectImage: () -> Unit,
    inAppSignUp: () -> Unit
) {
    val registrationViewModelState = registrationViewModel.registrationViewModelStates
    val userImage = rememberAsyncImagePainter(
        model =
        ImageRequest.Builder(LocalContext.current)
            .data(registrationViewModelState.image)
            .build()
    )
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
                painter = if (registrationViewModelState.image.isEmpty()) {
                    painterResource(id = R.drawable.select_image)
                } else {
                    userImage
                },
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier.clickable {
                    selectImage()
                }
            )
            Spacer(modifier = Modifier.padding(8.dp))

            CustomTextField(
                value = registrationViewModelState.email,
                leadingIcon = Icons.Filled.Person,
                onValueChange = { registrationViewModel.applyEmail(it) },
                label = "Enter email",
                isError = registrationViewModelState.isEmailError,
            )
            if (registrationViewModelState.isEmailError) {
                Text(
                    text = stringResource(R.string.email_error_message),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            CustomTextField(
                value = registrationViewModelState.password,
                leadingIcon = Icons.Filled.Password,
                onValueChange = { registrationViewModel.applyPassword(it) },
                label = "Enter password",
                visualTransformation =  PasswordVisualTransformation(),

                isError = registrationViewModelState.isPasswordError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )

            if (registrationViewModelState.isPasswordError) {
                Text(
                    text = stringResource(R.string.password_error_message),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            CustomTextField(
                value = registrationViewModelState.repeatPassword,
                leadingIcon = Icons.Filled.Password,
                onValueChange = { registrationViewModel.applyRepeatPassword(it) },
                label = "Repeat password",
                isError = registrationViewModelState.isRepeatPasswordError,
                visualTransformation =  PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )
            if (registrationViewModelState.isRepeatPasswordError) {
                Text(
                    text = stringResource(R.string.repeat_password_error_message),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            Button(onClick = inAppSignUp , modifier = Modifier.fillMaxWidth()) {
                Text(text = "Sign Up")
            }

        }
    }
}