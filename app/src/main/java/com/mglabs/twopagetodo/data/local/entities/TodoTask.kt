package com.mglabs.twopagetodo.data.local.entities

import com.mglabs.twopagetodo.shared.TaskStatus
import java.time.LocalDateTime
import com.mglabs.twopagetodo.domain.model.TodoTask as DomainTodoTask


data class TodoTask(
    var id: Int,
    var title: String,
    var content: String,
    var isFavorite: Boolean = false,
    var status: TaskStatus = TaskStatus.TODO,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var dueDate: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false,
    val isReminder: Boolean = false
)


fun TodoTask.toDomain(): DomainTodoTask {
    return DomainTodoTask(
        id,
        title,
        content,
        dueDate,
        isFavorite,
        status,
        createdAt,
        isDeleted,
        isReminder
    )
}