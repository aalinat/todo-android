package com.mglabs.twopagetodo.ui.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val todoTaskRepository: TodoTaskRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _uiState: MutableStateFlow<DetailsScreenState> =
        MutableStateFlow(DetailsScreenState.from(savedStateHandle))

    private var _formState: MutableStateFlow<DetailsFormState> =
        MutableStateFlow(_uiState.value.toForm())

    // getter
    val uiState = _uiState
    val formState = _formState


    /**
     * NAVBAR
     */
    fun favorite() {
        viewModelScope.launch {
            val updatedItem = todoTaskRepository.favorite(_uiState.value.id)
            updatedItem?.let {
                _uiState.value = updatedItem.transform()
            }
        }
    }

    fun unFavorite() {
        viewModelScope.launch {
            val updatedItem = todoTaskRepository.unFavorite(_uiState.value.id)
            updatedItem?.let {
                _uiState.value = updatedItem.transform()
            }
        }
    }

    fun deleteItem() {
        viewModelScope.launch {
            val updatedItem = todoTaskRepository.delete(_uiState.value.id)
            updatedItem?.let {
                _uiState.value = updatedItem.transform()
            }
        }
    }

    /**
     * FORM
     */
    fun onTitleValueChange(text: String) {
        _formState.value = _formState.value.copy(title = text)
    }

    fun onContentValueChange(text: String) {
        _formState.value = _formState.value.copy(content = text)
    }

    fun onValidateText(text: String): Boolean {
        return text.isNotEmpty()
    }


    fun onEditForm(): FormEvent? {
        val state = _formState.value;
        when (state.isEditMode) {
            true -> {
                if (onValidateText(_formState.value.title) && onValidateText(_formState.value.content)
                ) {
                    if (isFormChanged(_formState.value)) {
                        updateItem(_formState.value)
                        setEditMode(false)
                        return FormEvent.Update
                    }
                    setEditMode(false)
                } else {
                    return FormEvent.ValidationError
                }
            }

            false -> setEditMode(true)
        }
        return null
    }

    private fun setEditMode(isEditMode: Boolean) {
        _formState.value = _formState.value.copy(isEditMode = isEditMode)
    }

    private fun isFormChanged(form: DetailsFormState): Boolean {
        return form.title != _uiState.value.title || form.content != _uiState.value.content
    }

    private fun updateItem(form: DetailsFormState) {
        viewModelScope.launch {
            val daoItem =
                _uiState.value.copy(title = form.title, content = form.content).transform()
            val updatedItem = todoTaskRepository.update(todo = daoItem)
            updatedItem?.let {
                _uiState.value = updatedItem.transform()
            }
        }
    }

    sealed interface FormEvent {
        data object ValidationError : FormEvent
        data object Update : FormEvent
    }
}