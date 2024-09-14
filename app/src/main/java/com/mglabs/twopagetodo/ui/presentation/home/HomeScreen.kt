package com.mglabs.twopagetodo.ui.presentation.home

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mglabs.twopagetodo.domain.model.TodoTask
import com.mglabs.twopagetodo.ui.presentation.components.ItemList
import com.mglabs.twopagetodo.ui.presentation.components.LoadingContent
import com.mglabs.twopagetodo.ui.presentation.components.HomeScreenLayout


@Composable
fun HomeScreen(
    snackBarHostState: SnackbarHostState,
    viewModel: HomeScreenViewModel = hiltViewModel<HomeScreenViewModel>(),
    onNavigateToDetails: (TodoTask) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    HomeScreenLayout(snackBarHostState, viewModel::onFloatingActionClick) {
        when (val result = state) {
            is HomeScreenViewModel.State.Loading -> LoadingContent()
            is HomeScreenViewModel.State.Success -> ItemList(
                result.items,
                onEdit = { todo -> onNavigateToDetails(todo) },
                onDelete = { todo -> viewModel.deleteItem(todo.id) },
                onFavorite = { todo -> viewModel.favorite(todo.id) },
                onUnFavorite = { todo -> viewModel.unFavorite(todo.id) },
            )
        }
    }
}
