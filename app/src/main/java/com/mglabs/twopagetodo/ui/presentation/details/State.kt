package com.mglabs.twopagetodo.ui.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.mglabs.twopagetodo.domain.model.TodoTask
import com.mglabs.twopagetodo.shared.TaskStatus
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME


data class DetailsFormState(
    var title: String,
    var content: String,
    var createdAt: String,
    var isEditMode: Boolean,
    var titleError: String,
    var contentError: String
)


@Serializable
data class DetailsScreenState(
    var id: Int,
    var title: String,
    var content: String,
    var projectId: Int,
    var dueDate: String,
    var isFavorite: Boolean = false,
    var status: TaskStatus = TaskStatus.TODO,
    var createdAt: String,
    var isDeleted: Boolean = false
) {
    companion object {
        fun from(savedStateHandle: SavedStateHandle) =
            savedStateHandle.toRoute<DetailsScreenState>()
    }
}

fun DetailsScreenState.transform(): TodoTask {
    return TodoTask(
        id,
        title,
        content,
        projectId,
        LocalDateTime.parse(dueDate, formatter),
        isFavorite,
        TaskStatus.TODO,
        LocalDateTime.parse(createdAt, formatter),
        isDeleted
    )
}

fun DetailsScreenState.toForm(): DetailsFormState {
    return DetailsFormState(
        title = title,
        content = content,
        createdAt = createdAt,
        isEditMode = false,
        "",
        ""
    )
}

fun TodoTask.transform(): DetailsScreenState {
    return DetailsScreenState(
        id,
        title,
        content,
        projectId,
        formatter.format(dueDate),
        isFavorite,
        status,
        formatter.format(createdAt),
        isDeleted
    )
}