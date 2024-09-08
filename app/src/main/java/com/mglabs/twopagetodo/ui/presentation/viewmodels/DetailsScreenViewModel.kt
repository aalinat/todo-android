package com.mglabs.twopagetodo.ui.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import com.mglabs.twopagetodo.ui.navigation.DetailScreenUIModel
import com.mglabs.twopagetodo.ui.navigation.transform
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val todoTaskRepository: TodoTaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var _uiState: MutableStateFlow<DetailScreenUIModel> = MutableStateFlow(DetailScreenUIModel.from(savedStateHandle))
    // getter
    val uiState = _uiState
    fun updateItem(title: String, content: String) {
        viewModelScope.launch {
            val daoItem = _uiState.value.copy(title = title, content = content).transform()
            val updatedItem = todoTaskRepository.update(todo = daoItem)
            updatedItem?.let {
                _uiState.value = updatedItem.transform()
            }
        }
    }

    fun favorite() {
        viewModelScope.launch {
            val updatedItem = todoTaskRepository.favorite(_uiState.value.id)
            updatedItem?.let {
                _uiState.value = updatedItem.transform()
            }
        }
    }

    fun unFavorite() {
        viewModelScope.launch {
            val updatedItem = todoTaskRepository.unFavorite(_uiState.value.id)
            updatedItem?.let {
                _uiState.value = updatedItem.transform()
            }
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            val updatedItem = todoTaskRepository.delete(_uiState.value.id)
            updatedItem?.let {
                _uiState.value = updatedItem.transform()
            }
        }
    }

    fun setEditMode(isEditMode: Boolean) {
        _uiState.value = _uiState.value.copy(isEditMode = isEditMode)
    }
}