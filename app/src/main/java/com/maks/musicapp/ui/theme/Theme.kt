package com.maks.musicapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
//    primary = PrimaryDarkColor,
//    primaryVariant = PrimaryColor,
//    secondary = SecondaryDarkColor,
//    onPrimary = PrimaryTextColor,
//    onSurface = PrimaryDarkColor,
//    onSecondary = SecondaryTextColor,
//    secondaryVariant = SecondaryColor
    primary = PrimaryDarkColor,
    primaryVariant = PrimaryColor,
    onPrimary = Color.White,
    secondary = SecondaryDarkColor,
    secondaryVariant = SecondaryColor,
    onSecondary = Color.White,
    surface = PrimaryDarkColor,
    onSurface = Color.White,

)

private val LightColorPalette = lightColors(
//    primary = PrimaryLightColor,
//    primaryVariant = PrimaryColor,
//    secondary = SecondaryLightColor,
//    onPrimary = PrimaryTextColor,
//    onSecondary = SecondaryTextColor,
//    secondaryVariant = SecondaryColor,
    primary = PrimaryLightColor,
    primaryVariant = PrimaryColor,
    onPrimary = Color.Black,
    secondary = SecondaryLightColor,
    secondaryVariant = SecondaryColor,
    onSecondary = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,


    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MusicAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}