package com.mglabs.twopagetodo.ui.presentation.create

import com.mglabs.twopagetodo.ui.presentation.utils.getTodaysDate
import kotlinx.serialization.Serializable

@Serializable
data class CreateFormState(
    var title: String = "",
    var content: String = "",
    var dueDate: String = getTodaysDate(),
    var titleError: String = "",
    var contentError: String = "",
    var dueDateError: String = ""
) {
    fun getErrors(): String {
        val errors = listOf(titleError, contentError, dueDateError).filter { it.isNotEmpty() }
        if (errors.isEmpty()) {
            return "Validation Error"
        }
        return errors.joinToString("\n")
    }
}
