package com.mglabs.twopagetodo.ui.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.mglabs.twopagetodo.domain.TodoTask
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

@Serializable
object HomeScreenUIModel

@Serializable
data class TodoTaskUIModel(var id: Int,
                           var author: String,
                           var title: String,
                           var content: String,
                           var createdAt: String,
                           var isFavorite: Boolean = false,
                           var isDeleted: Boolean = false) {
    companion object {
        fun from(savedStateHandle: SavedStateHandle) =
            savedStateHandle.toRoute<TodoTaskUIModel>()
    }
}
fun TodoTaskUIModel.transform(): TodoTask {
    return TodoTask(
        id,
        author,
        title,
        content,
        isFavorite,
        LocalDateTime.parse(createdAt, formatter),
        isDeleted
    )
}
fun TodoTask.transform(): TodoTaskUIModel {
    return TodoTaskUIModel(
        id,
        author,
        title,
        content,
        formatter.format(createdAt),
        isFavorite,
        isDeleted
    )
}