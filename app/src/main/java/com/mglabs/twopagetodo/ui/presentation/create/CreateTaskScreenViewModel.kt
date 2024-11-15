package com.mglabs.twopagetodo.ui.presentation.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mglabs.twopagetodo.domain.model.Project
import com.mglabs.twopagetodo.domain.model.TodoTask
import com.mglabs.twopagetodo.domain.repository.ProjectRepository
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import com.mglabs.twopagetodo.shared.Config
import com.mglabs.twopagetodo.ui.presentation.home.HomeScreenViewModel.State
import com.mglabs.twopagetodo.ui.presentation.utils.validateText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.inject.Inject

@HiltViewModel
class CreateTaskScreenViewModel @Inject constructor(
    private val todoTaskRepository: TodoTaskRepository,
    private val projectRepository: ProjectRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(State.Loading)
    // getter
    val uiState = _uiState
    /**
     * FORM
     */
    private var _formState
    : MutableStateFlow<CreateFormState> =
        MutableStateFlow(
            CreateFormState()
        )
    // getter
    val formState = _formState

    init {
        fetchProjects()
    }

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
        _formState.value = _formState.value.copy(contentError = "content is invalid")
        return false
    }

    fun onDueDateValueChange(text: String) {
        _formState.value = _formState.value.copy(dueDate = text)
    }
    private fun fetchProjects() {
        viewModelScope.launch {
            _uiState.value = State.Loading
            projectRepository.findAll().collect {
                _uiState.value = State.Success(it)
            }

        }
    }
    suspend fun onSaveClick(): Boolean {
        if (!handleTitleValidation(_formState.value.title) or !handleContentValidation(_formState.value.content)) {
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
                    1,
                    date
                )
            )
            return true
        } catch (e: DateTimeParseException) {
            _formState.value = _formState.value.copy(dueDateError = "date formatting is invalid")
            return false
        }
    }
    sealed class State {
        data object Loading : State()
        data class Success(val projects: List<Project> = emptyList()) : State()
    }
}