package com.mglabs.twopagetodo.data.repository.room


import com.mglabs.twopagetodo.data.room.dao.TodoTaskDao
import com.mglabs.twopagetodo.data.room.entities.toDomainEntity
import com.mglabs.twopagetodo.domain.model.TodoTask
import com.mglabs.twopagetodo.domain.model.toRoomEntity
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TodoTaskRepositoryImpl @Inject constructor(private val todoTaskDao: TodoTaskDao) :
    TodoTaskRepository {

    override fun findAll(): Flow<List<TodoTask>> {
        return flow {
            todoTaskDao.getTodos().collect{ list -> emit(list.map { it.toDomainEntity()  })}
        }
    }

    override suspend fun findById(todoTaskId: Int): TodoTask {
        return todoTaskDao.getTodoTaskById(todoTaskId).toDomainEntity()
    }

    override suspend fun update(todo: TodoTask): TodoTask {
        todoTaskDao.update(todo.toRoomEntity())
        return findById(todo.id)
    }

    override suspend fun favorite(todoTaskId: Int): TodoTask {
        val todo = findById(todoTaskId)
        todo.isFavorite = true
        return update(todo)
    }

    override suspend fun unFavorite(todoTaskId: Int): TodoTask {
        val todo = findById(todoTaskId)
        todo.isFavorite = false
        return update(todo)
    }

    override suspend fun delete(todoTaskId: Int): Int {
        val todo = todoTaskDao.getTodoTaskById(todoTaskId)
        return todoTaskDao.deleteTodoTask(todo)
    }

    override suspend fun create(todo: TodoTask): TodoTask {
        todoTaskDao.insertTodoTask(todo.toRoomEntity())
        return todo
    }

}