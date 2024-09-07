package com.mglabs.twopagetodo.data.repository

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.mglabs.twopagetodo.domain.TodoTask
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import java.util.Date

class TodoTaskRepositoryImpl: TodoTaskRepository {
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
    override fun filterBy(predicate: (todo: TodoTask) -> Boolean): List<TodoTask> {
        return _todos.filter { predicate(it) }
    }

    override fun findAll(): List<TodoTask> {
        return filterBy  { !it.isDeleted }
    }

    override fun update(todo: TodoTask): TodoTask? {
        var foundItem = findById(todo.id)
        foundItem?.let {
            foundItem = todo
        }
        return foundItem
    }

    override fun favorite(id: Int): TodoTask? {
        val foundItem = findById(id)
        foundItem?.let {
            foundItem.isFavorite = true
        }
        return foundItem
    }

    override fun unFavorite(id: Int): TodoTask? {
        val foundItem = findById(id)
        foundItem?.let {
            foundItem.isFavorite = false
        }
        return foundItem
    }

    override fun delete(id: Int): TodoTask? {
        val foundItem = findById(id)
        foundItem?.let {
            foundItem.isDeleted = true
        }
        return foundItem
    }

    override fun create(todo: TodoTask): TodoTask {
        _todos.add(todo)
        return todo
    }

    override fun findById(todoTaskId: Int): TodoTask? {
        return _todos.find { it.id == todoTaskId }
    }
}