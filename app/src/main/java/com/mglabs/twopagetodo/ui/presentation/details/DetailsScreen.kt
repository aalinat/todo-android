package com.mglabs.twopagetodo.ui.presentation.details

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.mglabs.twopagetodo.ui.presentation.components.DetailsScreenLayout
import com.mglabs.twopagetodo.ui.presentation.components.ItemDetailsForm
import com.mglabs.twopagetodo.ui.presentation.details.DetailsScreenViewModel.FormEvent
import kotlinx.coroutines.launch


@Composable
fun DetailsScreen(snackBarHostState: SnackbarHostState, onNavigateToHome: () -> Unit) {
    val viewModel: DetailsScreenViewModel = hiltViewModel<DetailsScreenViewModel>()
    val state by viewModel.uiState.collectAsState()
    val formState by viewModel.formState.collectAsState()
    val onEditClicked: () -> Unit =  {
        viewModel.viewModelScope.launch {
            val result: FormEvent? = viewModel.onEditForm()
            result?.let {
                when (it) {
                    FormEvent.Update -> snackBarHostState.showSnackbar("updated!!")
                    FormEvent.ValidationError -> snackBarHostState.showSnackbar("validation error!!")
                }
            }
        }
    }

    val onDeleteClicked: () -> Unit = {
        viewModel.viewModelScope.launch {
            viewModel.deleteItem()
            onNavigateToHome()
            snackBarHostState.showSnackbar("deleted!!")
        }
    }

    DetailsScreenLayout(
        formState = formState,
        isFavorite = state.isFavorite,
        snackBarHostState = snackBarHostState,
        onTitleValueChange = viewModel::onTitleValueChange,
        onNavigateToHome = onNavigateToHome,
        onEditClicked = onEditClicked,
        onDelete = onDeleteClicked,
        onFavorite = viewModel::favorite,
        onUnFavorite = viewModel::unFavorite,
    ) {
        ItemDetailsForm(
            formState,
            viewModel::onContentValueChange
        )
    }
}