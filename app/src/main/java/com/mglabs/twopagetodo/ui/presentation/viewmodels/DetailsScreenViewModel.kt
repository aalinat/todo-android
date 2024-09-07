package com.mglabs.twopagetodo.ui.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import com.mglabs.twopagetodo.ui.navigation.TodoTaskUIModel
import com.mglabs.twopagetodo.ui.navigation.transform
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val todoTaskRepository: TodoTaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var item: MutableState<TodoTaskUIModel> = mutableStateOf( TodoTaskUIModel.from(savedStateHandle))

    fun getTodoTask(): TodoTaskUIModel {
        return item.value
    }
    fun updateItem(todo: TodoTaskUIModel) {
        viewModelScope.launch {
            val updatedItem = todoTaskRepository.update(todo = todo.transform())
            updatedItem?.let {
                item.value = updatedItem.transform()
            }
        }
    }

    fun favorite() {
        viewModelScope.launch {
            val updatedItem = todoTaskRepository.favorite(item.value.id)
            updatedItem?.let {
                item.value = updatedItem.transform()
            }
        }
    }

    fun unFavorite() {
        viewModelScope.launch {
            val updatedItem = todoTaskRepository.unFavorite(item.value.id)
            updatedItem?.let {
                item.value = updatedItem.transform()
            }
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            val updatedItem = todoTaskRepository.delete(item.value.id)
            updatedItem?.let {
                item.value = updatedItem.transform()
            }
        }
    }
}