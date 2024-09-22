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
        var errors = titleError + contentError + dueDateError
        if (errors.isEmpty()) {
            errors = "Validation Error"
        }
        return errors
    }
}
