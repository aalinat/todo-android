package com.mglabs.twopagetodo.ui.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.mglabs.twopagetodo.domain.TodoTask
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME


data class DetailsFormState(var title: String, var content: String, var author: String, var createdAt: String, var isEditMode: Boolean)


@Serializable
data class DetailsScreenState(
    var id: Int,
    var author: String,
    var title: String,
    var content: String,
    var createdAt: String,
    var isFavorite: Boolean = false,
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
        author,
        title,
        content,
        isFavorite,
        LocalDateTime.parse(createdAt, formatter),
        isDeleted
    )
}

fun DetailsScreenState.toForm(): DetailsFormState {
    return DetailsFormState(
        title = title,
        content = content,
        author = author,
        createdAt = createdAt,
        isEditMode = false
    )
}

fun TodoTask.transform(): DetailsScreenState {
    return DetailsScreenState(
        id,
        author,
        title,
        content,
        formatter.format(createdAt),
        isFavorite,
        isDeleted
    )
}