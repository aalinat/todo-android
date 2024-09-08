package com.mglabs.twopagetodo.ui.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.mglabs.twopagetodo.ui.presentation.components.EditableText
import com.mglabs.twopagetodo.ui.presentation.layouts.AppBarLayout
import com.mglabs.twopagetodo.ui.presentation.viewmodels.DetailsScreenViewModel
import com.mglabs.twopagetodo.ui.theme.editableTitleOutlinedTextFieldColors
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Composable
fun DetailsScreen(snackBarHostState: SnackbarHostState, onNavigateToHome: () -> Unit) {
    val viewModel: DetailsScreenViewModel = hiltViewModel<DetailsScreenViewModel>()
    val state by viewModel.uiState.collectAsState()

    val contentValue = remember {
        mutableStateOf(state.content)
    }
    val titleValue = remember {
        mutableStateOf(state.title)
    }
    val onValidate = { text: String -> text.isNotEmpty() }
    AppBarLayout(
        title = {
            EditableText(
                onValidate = onValidate,
                isEditMode = state.isEditMode,
                content = titleValue.value,
                colors = MaterialTheme.colorScheme.editableTitleOutlinedTextFieldColors,
                onValueChange = { text: String ->
                    titleValue.value = text
                }
            )
        },
        onPrev = onNavigateToHome,
        snackBarHostState = snackBarHostState,
        navActions = {
            FavoriteAction(state.isFavorite) {
                when (state.isFavorite) {
                    true -> viewModel.unFavorite()
                    false -> viewModel.favorite()
                }
            }
            UpdateAction(isEditMode = state.isEditMode) {
                when (state.isEditMode) {
                    true -> {
                        if (onValidate(titleValue.value) && onValidate(contentValue.value)
                        ) {
                            if ((titleValue.value != state.title || contentValue.value != state.content)) {
                                viewModel.viewModelScope.launch {
                                    viewModel.updateItem(titleValue.value, contentValue.value)
                                    snackBarHostState.showSnackbar("Updated...")
                                }
                            }
                            viewModel.setEditMode(false)
                        } else {
                            viewModel.viewModelScope.launch {
                                snackBarHostState.showSnackbar("Validation error!")
                            }
                        }
                    }

                    false -> viewModel.setEditMode(true)
                }
            }
            DeleteAction { viewModel.deleteItem() }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
            ) {
                Text(
                    text = toFormattedDate(state.createdAt),
                    color = MaterialTheme.colorScheme.secondary,
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(text = "By ${state.author}")
                Spacer(modifier = Modifier.padding(10.dp))
                EditableText(
                    isEditMode = state.isEditMode,
                    content = contentValue.value, onValueChange = { text: String ->
                        contentValue.value = text
                    }, onValidate = onValidate
                )
            }
        })

}

@Composable
fun FavoriteAction(isFavorite: Boolean, onAction: () -> Unit) {
    IconButton(onClick = {
        onAction()
    }) {
        FavIcon(isFavorite)
    }
}

@Composable
fun FavIcon(isFavorite: Boolean) {
    var favIcon = Icons.Default.FavoriteBorder
    var favIconDescription = "Mark as Favorite"
    if (isFavorite) {
        favIcon = Icons.Default.Favorite
        favIconDescription = "Unmark as Favorite"
    }
    Icon(imageVector = favIcon, contentDescription = favIconDescription)
}

@Composable
fun UpdateAction(isEditMode: Boolean = false, onUpdate: () -> Unit) {
    var imageVector = Icons.Outlined.Edit
    if (isEditMode) {
        imageVector = Icons.Outlined.Check
    }
    IconButton(onClick = onUpdate) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Edit Item"
        )
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

fun toFormattedDate(date: String): String {
    return LocalDateTime.parse(date).format(DateTimeFormatter.ofPattern("yyyy-mm-dd HH:mm"))
}