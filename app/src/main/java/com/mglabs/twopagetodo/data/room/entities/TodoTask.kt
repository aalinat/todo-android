package com.mglabs.twopagetodo.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mglabs.twopagetodo.shared.TaskStatus
import java.time.LocalDateTime
import com.mglabs.twopagetodo.domain.model.TodoTask as DomainTodoTask

@Entity(tableName = "todos")
data class TodoTask(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var title: String,
    var content: String,
    var isFavorite: Boolean = false,
    var status: TaskStatus = TaskStatus.TODO,
    var createdAt: LocalDateTime= LocalDateTime.now(),
    var dueDate: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false,
    val isReminder: Boolean = false
)


fun TodoTask.toDomainEntity(): DomainTodoTask {
    return DomainTodoTask(
        id,
        title,
        content,
        dueDate,
        isFavorite,
        status,
        createdAt,
        isFavorite,
        isReminder
    )
}