package com.mglabs.twopagetodo.ui.presentation.create

import androidx.lifecycle.ViewModel
import com.mglabs.twopagetodo.domain.model.TodoTask
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import com.mglabs.twopagetodo.shared.Config
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateTaskScreenViewModel @Inject constructor(
    private val todoTaskRepository: TodoTaskRepository,
) : ViewModel() {
    private var _formState: MutableStateFlow<CreateFormState> =
        MutableStateFlow(
            CreateFormState("", "", "")
        )

    // getter
    val formState = _formState
    /**
     * FORM
     */
    fun onTitleValueChange(text: String) {
        _formState.value = _formState.value.copy(title = text)
    }

    fun onContentValueChange(text: String) {
        _formState.value = _formState.value.copy(content = text)
    }

    fun onDueDateValueChange(text: String) {
        _formState.value = _formState.value.copy(dueDate = text)
    }

    suspend fun onSaveClick() {
        val localDate = LocalDate.parse(_formState.value.dueDate, DateTimeFormatter.ofPattern(Config.DATE_PATTERN))
        val date = LocalDateTime.ofInstant(localDate.atStartOfDay().toInstant(ZoneOffset.UTC), ZoneOffset.UTC)
        todoTaskRepository.create(TodoTask(0, _formState.value.title, _formState.value.content, date))
    }
}