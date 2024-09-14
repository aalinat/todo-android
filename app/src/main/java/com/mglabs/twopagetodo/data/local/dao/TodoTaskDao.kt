package com.mglabs.twopagetodo.data.local.dao

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.mglabs.twopagetodo.data.local.entities.TodoTask
import javax.inject.Singleton

@Singleton
class TodoTaskDao  {
    private val _todos: MutableList<TodoTask> = mutableListOf()

    init {
        repeat(5) {
            _todos.add(
                TodoTask(
                    it,
                    "Todo $it",
                    LoremIpsum(100).values.first()
                )
            )
        }
    }

    fun filterBy(predicate: (todo: TodoTask) -> Boolean): List<TodoTask> {
        return _todos.filter { predicate(it) }
    }

    fun findAll(): List<TodoTask> {
        return filterBy { !it.isDeleted }
    }

    fun update(todo: TodoTask): TodoTask {
        _todos.replaceAll { if (it.id == todo.id) todo else it }
        return todo
    }

    fun favorite(id: Int): TodoTask? {
        val foundItem = findById(id)
        foundItem?.let {
            foundItem.isFavorite = true
        }
        return foundItem
    }

    fun unFavorite(id: Int): TodoTask? {
        val foundItem = findById(id)
        foundItem?.let {
            foundItem.isFavorite = false
        }
        return foundItem
    }

    fun delete(id: Int): TodoTask? {
        val foundItem = findById(id)
        foundItem?.let {
            foundItem.isDeleted = true
        }
        return foundItem
    }

    fun create(todo: TodoTask): TodoTask {
        _todos.add(todo)
        return todo
    }

     fun findById(todoTaskId: Int): TodoTask? {
        return _todos.find { it.id == todoTaskId }
    }
}