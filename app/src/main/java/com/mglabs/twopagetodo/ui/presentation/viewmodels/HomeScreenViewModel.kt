package com.mglabs.twopagetodo.ui.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mglabs.twopagetodo.domain.TodoTask
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel  @Inject constructor(
    private val todoTaskRepository: TodoTaskRepository
) : ViewModel()  {

    val mutableState = MutableStateFlow<State>(State.Result(mutableListOf()))
    sealed class State {
        data object Loading : State()
        data class Result(var items: List<TodoTask>) : State()
    }
    fun getItems() {
        viewModelScope.launch {
            mutableState.value = State.Result(items = todoTaskRepository.findAll())
        }
    }
    fun addItem(todo: TodoTask) {
        viewModelScope.launch {
            todoTaskRepository.create(todo)
            mutableState.value = State.Result(items = todoTaskRepository.findAll())
        }
    }

    fun deleteItem(id: Int) {
        viewModelScope.launch {
            todoTaskRepository.delete(id)
            mutableState.value = State.Result(items = todoTaskRepository.findAll())
        }
    }
}