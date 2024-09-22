package com.mglabs.twopagetodo.ui.presentation.create

import android.util.Log
import androidx.datastore.preferences.protobuf.Internal.BooleanList
import androidx.lifecycle.ViewModel
import com.mglabs.twopagetodo.domain.model.TodoTask
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import com.mglabs.twopagetodo.shared.Config
import com.mglabs.twopagetodo.ui.presentation.utils.toFormattedDate
import com.mglabs.twopagetodo.ui.presentation.utils.validateText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

@HiltViewModel
class CreateTaskScreenViewModel @Inject constructor(
    private val todoTaskRepository: TodoTaskRepository,
) : ViewModel() {
    private var _formState: MutableStateFlow<CreateFormState> =
        MutableStateFlow(
            CreateFormState()
        )

    // getter
    val formState = _formState

    /**
     * FORM
     */
    fun onTitleValueChange(text: String) {
        _formState.value = _formState.value.copy(title = text)
        handleTitleValidation(text)
    }

    fun onContentValueChange(text: String) {
        _formState.value = _formState.value.copy(content = text)
        handleContentValidation(text)
    }

    private fun handleTitleValidation(text: String): Boolean {
        if (validateText(text)) {
            _formState.value = _formState.value.copy(titleError = "")
            return true
        }
        _formState.value = _formState.value.copy(titleError = "title is invalid")
        return false
    }

    private fun handleContentValidation(text: String): Boolean {
        if (validateText(text)) {
            _formState.value = _formState.value.copy(contentError = "")
            return true
        }
        _formState.value = _formState.value.copy(contentError = "content field is invalid")
        return false
    }

    fun onDueDateValueChange(text: String) {
        _formState.value = _formState.value.copy(dueDate = text)
    }

    suspend fun onSaveClick(): Boolean {
        if (!handleTitleValidation(_formState.value.title) || !handleContentValidation(_formState.value.content)) {
            return false
        }
        try {
            val localDate = LocalDate.parse(
                _formState.value.dueDate,
                DateTimeFormatter.ofPattern(Config.DATE_PATTERN)
            )
            val date = LocalDateTime.ofInstant(
                localDate.atStartOfDay().toInstant(ZoneOffset.UTC),
                ZoneOffset.UTC
            )
            val today = LocalDateTime.ofInstant(
                LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC),
                ZoneOffset.UTC
            )
            if (date < today) {
                _formState.value =
                    _formState.value.copy(dueDateError = "due date cannot be is the past")
                return false
            }
            todoTaskRepository.create(
                TodoTask(
                    0,
                    _formState.value.title,
                    _formState.value.content,
                    date
                )
            )
            return true
        } catch (e: DateTimeParseException) {
            _formState.value = _formState.value.copy(dueDateError = "date formatting is invalid")
            return false
        }
    }
}