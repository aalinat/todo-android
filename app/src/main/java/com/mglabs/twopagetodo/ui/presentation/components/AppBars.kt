package com.mglabs.twopagetodo.ui.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.mglabs.twopagetodo.ui.presentation.details.DetailsFormState
import com.mglabs.twopagetodo.ui.presentation.utils.validateText
import com.mglabs.twopagetodo.ui.theme.editableTitleOutlinedTextFieldColors


@Composable
fun HomeScreenLayout(
    snackBarHostState: SnackbarHostState,
    onFloatingActionClick: () -> Unit,
    content: @Composable () -> Unit
) {
    AppBarLayout(
        title = {
            Text(text = "My Todo List", color = Color.Red)
        },
        snackBarHostState = snackBarHostState,
        floatingButton = { FloatingButton(onFloatingActionClick) },
        content = { content() })
}

@Composable
fun DetailsScreenLayout(
    formState: DetailsFormState,
    isFavorite: Boolean,
    snackBarHostState: SnackbarHostState,
    onTitleValueChange: (text: String) -> Unit,
    onNavigateToHome: (() -> Unit)? = null,
    onEditClicked: () -> Unit,
    onDelete: () -> Unit,
    onFavorite: () -> Unit,
    onUnFavorite: () -> Unit,
    content: @Composable () -> Unit
) {
    AppBarLayout(
        title = {
            EditableText(
                onValidate = {text: String -> validateText(text)},
                isEditMode = formState.isEditMode,
                content = formState.title,
                colors = MaterialTheme.colorScheme.editableTitleOutlinedTextFieldColors,
                onValueChange = onTitleValueChange
            )
        },
        onPrev = onNavigateToHome,
        snackBarHostState = snackBarHostState,
        navActions = {
            FavoriteAction(isFavorite) {
                when (isFavorite) {
                    true -> onUnFavorite()
                    false -> onFavorite()
                }
            }
            EditButton(
                isEditMode = formState.isEditMode,
                onClick = onEditClicked
            )
            DeleteAction { onDelete() }
        },
        content = {
            content()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarLayout(
    title: @Composable () -> Unit,
    content: @Composable () -> Unit,
    snackBarHostState: SnackbarHostState,
    navActions: @Composable (() -> Unit)? = null,
    floatingButton: @Composable (() -> Unit)? = null,
    onPrev: (() -> Unit)? = null,
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        floatingActionButton = {
            if (floatingButton != null) {
                floatingButton()
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            AppBar(title, scrollBehavior, { navActions?.let { it() } }, onPrev)
        }, content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                content()
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    title: @Composable () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    navActions: @Composable () -> Unit,
    onPrev: (() -> Unit)? = null
) {
    TopAppBar(title = {
        title()
    },
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            onPrev?.let {
                IconButton(onClick = { onPrev() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Go Back"
                    )
                }
            }
        },
        actions = {
            navActions()
        })
}