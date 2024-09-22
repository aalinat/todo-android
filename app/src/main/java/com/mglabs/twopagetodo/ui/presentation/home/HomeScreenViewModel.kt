package com.mglabs.twopagetodo.ui.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mglabs.twopagetodo.domain.model.TodoTask
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val todoTaskRepository: TodoTaskRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    // getter
    val uiState = _uiState

    init {
        fetchTasks()
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch {
            todoTaskRepository.delete(id)
            fetchTasks()
        }
    }

    fun favorite(id: Int) {
        viewModelScope.launch {
            val updatedItem = todoTaskRepository.favorite(id)
            updatedItem?.let {
                fetchTasks()
            }
        }
    }

    fun unFavorite(id: Int) {
        viewModelScope.launch {
            val updatedItem = todoTaskRepository.unFavorite(id)
            updatedItem?.let {
                fetchTasks()
            }
        }
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            _uiState.value = State.Loading
            todoTaskRepository.findAll().collect {
                _uiState.value = State.Success(it)
            }

        }
    }

    sealed class State {
        data object Loading : State()
        data class Success(val items: List<TodoTask> = emptyList()) : State()
    }
}