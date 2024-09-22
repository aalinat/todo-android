package com.mglabs.twopagetodo.widget.ui.state

import com.mglabs.twopagetodo.shared.TaskStatus
import kotlinx.serialization.Serializable

@Serializable
sealed interface TodoState {
    @Serializable
    data object Loading : TodoState

    @Serializable
    data class Success(val todos: List<WidgetItem>) : TodoState
}

@Serializable
data class WidgetItem(
    val id: Int,
    val title: String,
    val isFavorite: Boolean = false,
    val status: TaskStatus = TaskStatus.TODO
)