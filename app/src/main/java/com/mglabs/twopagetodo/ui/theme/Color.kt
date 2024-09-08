package com.mglabs.twopagetodo.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

internal val ColorScheme.editableOutlinedTextFieldColors: TextFieldColors
    @Composable
    get() {
        return OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            disabledBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            disabledTextColor = Color.Black
        )
    }

internal val ColorScheme.editableTitleOutlinedTextFieldColors: TextFieldColors
    @Composable
    get() {
        return editableOutlinedTextFieldColors.copy(
            disabledTextColor = Color.Red,
            focusedTextColor = Color.Red,
            unfocusedTextColor = Color.Red
        )
    }