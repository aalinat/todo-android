package com.mglabs.twopagetodo.widget
import kotlinx.serialization.Serializable

@Serializable
sealed interface TodoState {
    @Serializable
    data object Loading : TodoState

    @Serializable
    data object Success : TodoState
}