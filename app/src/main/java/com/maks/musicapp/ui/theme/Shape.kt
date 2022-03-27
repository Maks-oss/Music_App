package com.maks.musicapp.ui.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    small = RoundedCornerShape(16.dp),
    medium = CutCornerShape(bottomEnd = 16.dp),
    large = RoundedCornerShape(0.dp)
)