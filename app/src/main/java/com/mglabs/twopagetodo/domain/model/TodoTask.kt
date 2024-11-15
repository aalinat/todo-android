package com.mglabs.twopagetodo.domain.model

import com.mglabs.twopagetodo.shared.TaskStatus
import java.time.LocalDateTime
import com.mglabs.twopagetodo.data.local.entities.TodoTask as LocalTodoTask
import com.mglabs.twopagetodo.data.room.entities.TodoTask as RoomTodoTask

data class TodoTask(
    var id: Int,
    var title: String,
    var content: String,
    var projectId: Int,
    var dueDate: LocalDateTime = LocalDateTime.now(),
    var isFavorite: Boolean = false,
    var status: TaskStatus = TaskStatus.TODO,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false,
    val isReminder: Boolean = false
)

fun TodoTask.toLocalEntity(): LocalTodoTask {
    return LocalTodoTask(
        id,
        title,
        content,
        projectId,
        isFavorite,
        status,
        createdAt,
        dueDate,
        isDeleted,
        isReminder
    )
}

fun TodoTask.toRoomEntity(): RoomTodoTask {
    return RoomTodoTask(
        id,
        title,
        content,
        projectId,
        isFavorite,
        status,
        createdAt,
        dueDate,
        isDeleted,
        isReminder
    )
}