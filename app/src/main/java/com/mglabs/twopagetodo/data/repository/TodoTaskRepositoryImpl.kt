package com.mglabs.twopagetodo.data.repository

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.mglabs.twopagetodo.domain.TodoTask
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository

class TodoTaskRepositoryImpl : TodoTaskRepository {
    private val _todos: MutableList<TodoTask> = mutableListOf()

    init {
        repeat(5) {
            _todos.add(
                TodoTask(
                    it,
                    "Ahmad",
                    "Todo $it",
                    LoremIpsum(100).values.first()
                )
            )
        }
    }

    override suspend fun filterBy(predicate: (todo: TodoTask) -> Boolean): List<TodoTask> {
        return _todos.filter { predicate(it) }
    }

    override suspend fun findAll(): List<TodoTask> {
        return filterBy { !it.isDeleted }
    }

    override suspend fun update(todo: TodoTask): TodoTask {
        _todos.replaceAll { if (it.id == todo.id) todo else it }
        return todo
    }

    override suspend fun favorite(id: Int): TodoTask? {
        val foundItem = findById(id)
        foundItem?.let {
            foundItem.isFavorite = true
        }
        return foundItem
    }

    override suspend fun unFavorite(id: Int): TodoTask? {
        val foundItem = findById(id)
        foundItem?.let {
            foundItem.isFavorite = false
        }
        return foundItem
    }

    override suspend fun delete(id: Int): TodoTask? {
        val foundItem = findById(id)
        foundItem?.let {
            foundItem.isDeleted = true
        }
        return foundItem
    }

    override suspend fun create(todo: TodoTask): TodoTask {
        _todos.add(todo)
        return todo
    }

    override suspend fun findById(todoTaskId: Int): TodoTask? {
        return _todos.find { it.id == todoTaskId }
    }
}