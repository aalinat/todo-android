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
    private val todoTaskRepository: TodoTaskRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    // getter
    val uiState = _uiState

    init {
        viewModelScope.launch {
            delay(3000)
            _uiState.value = State.Success(items = todoTaskRepository.findAll())
        }
    }

    fun addItem(todo: TodoTask) {
        viewModelScope.launch {
            todoTaskRepository.create(todo)
            _uiState.value = State.Success(items = todoTaskRepository.findAll())
        }
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch {
            todoTaskRepository.delete(id)
            _uiState.value = State.Success(items = todoTaskRepository.findAll())
        }
    }

    sealed class State {
        data object Loading : State()
        data class Success(val items: List<TodoTask> = emptyList()) : State()
    }
}