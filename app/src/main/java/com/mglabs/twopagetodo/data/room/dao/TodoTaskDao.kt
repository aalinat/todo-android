package com.mglabs.twopagetodo.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mglabs.twopagetodo.data.room.entities.TodoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoTaskDao {
    @Query("SELECT * FROM todos")
    fun getTodos(): Flow<List<TodoTask>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodoTask(todoTask: TodoTask)

    @Delete
    suspend fun deleteTodoTask(todoTask: TodoTask): Int

    @Query("SELECT * FROM todos WHERE id = :todoTaskId")
    suspend fun getTodoTaskById(todoTaskId: Int): TodoTask

    @Update
    suspend fun update(todoTask: TodoTask): Int
}