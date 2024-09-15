package com.mglabs.twopagetodo.ui.presentation.components

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.mglabs.twopagetodo.shared.Config
import com.mglabs.twopagetodo.ui.theme.editableOutlinedTextFieldColors
import java.util.Date
import java.util.Locale


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
fun SaveButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = {
            onClick()
        },
    ) {
        Icon(Icons.Outlined.Check, "Add Item")
    }
}

@Composable
fun EditableText(
    isEditMode: Boolean,
    content: String,
    colors: TextFieldColors = MaterialTheme.colorScheme.editableOutlinedTextFieldColors,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    onValueChange: (text: String) -> Unit,
    onValidate: (String) -> Boolean,
    isSingleLine: Boolean = false,
    label: String = ""
) {
    OutlinedTextField(
        shape = RoundedCornerShape(8.dp),
        colors = colors,
        label = { Text(label) },
        textStyle = textStyle,
        readOnly = !isEditMode,
        enabled = isEditMode,
        isError = !onValidate(content),
        value = content,
        onValueChange = onValueChange,
        singleLine = isSingleLine,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
    )
}

@Composable
fun FavoriteAction(isFavorite: Boolean, onAction: () -> Unit) {
    IconButton(onClick = {
        onAction()
    }, modifier = Modifier.size(24.dp)) {
        var favIcon = Icons.Default.FavoriteBorder
        var favIconDescription = "Mark as Favorite"
        if (isFavorite) {
            favIcon = Icons.Default.Favorite
            favIconDescription = "Unmark as Favorite"
        }
        Icon(imageVector = favIcon, contentDescription = favIconDescription, tint = Color.Red)
    }
}

@Composable
fun DeleteAction(onDelete: () -> Unit) {
    IconButton(onClick = onDelete, modifier = Modifier.size(24.dp)) {
        Icon(
            imageVector = Icons.Outlined.Delete,
            contentDescription = "Delete Item"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDocked(
    label: String, onChange: (text: String) -> Unit,
    datePattern: String = Config.DATE_PATTERN
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it, datePattern = datePattern)
    } ?: ""
    onChange(selectedDate)
    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                autoCorrectEnabled = false,
            ),
            value = selectedDate,
            onValueChange = {},
            label = { Text(label) },
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { showDatePicker = !showDatePicker }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "Select date"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
        )

        if (showDatePicker) {
            Popup(
                onDismissRequest = { showDatePicker = false },
                alignment = Alignment.TopStart
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(y = 64.dp)
                        .shadow(elevation = 4.dp)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    DatePicker(
                        state = datePickerState,
                        showModeToggle = false,
                    )
                }
            }
        }
    }
}

fun convertMillisToDate(millis: Long, datePattern: String): String {
    val formatter = SimpleDateFormat(datePattern, Locale.getDefault())
    return formatter.format(Date(millis))
}