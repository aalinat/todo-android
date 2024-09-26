package com.mglabs.twopagetodo.data.repository.local


import com.mglabs.twopagetodo.data.local.dao.TodoTaskDao
import com.mglabs.twopagetodo.data.local.entities.toDomain
import com.mglabs.twopagetodo.domain.model.TodoTask
import com.mglabs.twopagetodo.domain.model.toLocalEntity
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TodoTaskRepositoryImpl : TodoTaskRepository {
    private val todoTaskDao: TodoTaskDao = TodoTaskDao()

    override fun findAll(): Flow<List<TodoTask>> {
        return flowOf(todoTaskDao.findAll().map { it.toDomain() })
    }

    override suspend fun update(todo: TodoTask): TodoTask {
        return todoTaskDao.update(todo.toLocalEntity()).toDomain()
    }

    override suspend fun favorite(todoTaskId: Int): TodoTask? {
        return todoTaskDao.favorite(todoTaskId)?.toDomain()
    }

    override suspend fun unFavorite(todoTaskId: Int): TodoTask? {
        return todoTaskDao.unFavorite(todoTaskId)?.toDomain()
    }

    override suspend fun delete(todoTaskId: Int): Int {
        return todoTaskDao.delete(todoTaskId)?.id ?: -1
    }

    override suspend fun create(todo: TodoTask): TodoTask {
        return todoTaskDao.create(todo.toLocalEntity()).toDomain()
    }

    override suspend fun findById(todoTaskId: Int): TodoTask? {
        return todoTaskDao.findById(todoTaskId)?.toDomain()
    }
}