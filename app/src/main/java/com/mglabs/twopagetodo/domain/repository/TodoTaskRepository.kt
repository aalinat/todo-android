package com.mglabs.twopagetodo.domain.repository

import com.mglabs.twopagetodo.domain.TodoTask

interface TodoTaskRepository {
    suspend fun filterBy(predicate: (todo: TodoTask) -> Boolean): List<TodoTask>
    suspend fun findAll(): List<TodoTask>
    suspend fun update(todo: TodoTask): TodoTask?
    suspend fun favorite(id: Int): TodoTask?
    suspend fun unFavorite(id: Int): TodoTask?
    suspend fun delete(id: Int): TodoTask?
    suspend fun create(todo: TodoTask): TodoTask
    suspend fun findById(todoTaskId: Int): TodoTask?
}