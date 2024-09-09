package com.mglabs.twopagetodo.ui.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.mglabs.twopagetodo.ui.theme.editableOutlinedTextFieldColors

@Composable
fun EditButton(isEditMode: Boolean = false, onClick: () -> Unit) {
    var imageVector = Icons.Outlined.Edit
    if (isEditMode) {
        imageVector = Icons.Outlined.Check
    }
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Edit Item"
        )
    }
}

@Composable
fun FloatingButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = {
            onClick()
        },
    ) {
        Icon(Icons.Filled.Add, "Add Item")
    }
}

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

@Composable
fun FavoriteAction(isFavorite: Boolean, onAction: () -> Unit) {
    IconButton(onClick = {
        onAction()
    }) {
        var favIcon = Icons.Default.FavoriteBorder
        var favIconDescription = "Mark as Favorite"
        if (isFavorite) {
            favIcon = Icons.Default.Favorite
            favIconDescription = "Unmark as Favorite"
        }
        Icon(imageVector = favIcon, contentDescription = favIconDescription)
    }
}

@Composable
fun DeleteAction(onDelete: () -> Unit) {
    IconButton(onClick = onDelete) {
        Icon(
            imageVector = Icons.Outlined.Delete,
            contentDescription = "Delete Item"
        )
    }
}