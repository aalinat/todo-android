package com.mglabs.twopagetodo.ui.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mglabs.twopagetodo.domain.TodoTask
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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

    fun addItem(todo: TodoTask) {
        viewModelScope.launch {
            todoTaskRepository.create(todo)
            fetchTasks()
        }
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch {
            todoTaskRepository.delete(id)
            fetchTasks()
        }
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            _uiState.value = State.Loading
            delay(1000)
            _uiState.value = State.Success(todoTaskRepository.findAll())
        }
    }

    sealed class State {
        data object Loading : State()
        data class Success(val items: List<TodoTask> = emptyList()) : State()
    }
}