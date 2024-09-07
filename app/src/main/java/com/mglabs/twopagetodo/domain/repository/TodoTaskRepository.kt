package com.mglabs.twopagetodo.domain.repository

import com.mglabs.twopagetodo.domain.TodoTask

interface TodoTaskRepository {
    fun filterBy(predicate: (todo: TodoTask) -> Boolean): List<TodoTask>
    fun findAll(): List<TodoTask>
    fun update(todo: TodoTask): TodoTask?
    fun favorite(id: Int): TodoTask?
    fun unFavorite(id: Int): TodoTask?
    fun delete(id: Int): TodoTask?
    fun create(todo: TodoTask): TodoTask
    fun findById(todoTaskId: Int): TodoTask?
}