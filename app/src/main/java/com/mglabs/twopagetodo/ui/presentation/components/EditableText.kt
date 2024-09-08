package com.mglabs.twopagetodo.ui.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.mglabs.twopagetodo.ui.theme.editableOutlinedTextFieldColors

@Composable
fun EditableText(
    isEditMode: Boolean,
    content: String,
    colors: TextFieldColors = MaterialTheme.colorScheme.editableOutlinedTextFieldColors,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    onValueChange: (text: String) -> Unit,
    onValidate: (String) -> Boolean
) {
    OutlinedTextField(
        shape = RoundedCornerShape(8.dp),
        colors = colors,
        textStyle = textStyle,
        readOnly = !isEditMode,
        enabled = isEditMode,
        isError = !onValidate(content),
        value = content,
        onValueChange = onValueChange
    )
}