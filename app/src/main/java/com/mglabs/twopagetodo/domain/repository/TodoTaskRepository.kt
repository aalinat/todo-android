package com.mglabs.twopagetodo.domain.repository

import com.mglabs.twopagetodo.domain.model.TodoTask
import kotlinx.coroutines.flow.Flow


interface TodoTaskRepository {
    fun findAll(): Flow<List<TodoTask>>
    suspend fun findById(todoTaskId: Int): TodoTask?
    suspend fun update(todo: TodoTask): TodoTask?
    suspend fun favorite(todoTaskId: Int): TodoTask?
    suspend fun unFavorite(todoTaskId: Int): TodoTask?
    suspend fun delete(todoTaskId: Int): Int
    suspend fun create(todo: TodoTask): TodoTask
}