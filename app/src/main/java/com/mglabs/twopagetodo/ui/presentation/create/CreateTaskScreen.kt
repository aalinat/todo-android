package com.mglabs.twopagetodo.ui.presentation.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.mglabs.twopagetodo.ui.presentation.components.CreateTaskScreenLayout
import com.mglabs.twopagetodo.ui.presentation.components.DatePickerDocked
import com.mglabs.twopagetodo.ui.presentation.components.EditableText
import com.mglabs.twopagetodo.ui.presentation.components.SaveButton
import com.mglabs.twopagetodo.ui.presentation.utils.validateText
import com.mglabs.twopagetodo.ui.theme.editableTitleOutlinedTextFieldColors
import kotlinx.coroutines.launch

@Composable
fun CreateTaskScreen(snackBarHostState: SnackbarHostState, onNavigateToHome: () -> Unit) {
    val viewModel: CreateTaskScreenViewModel = hiltViewModel<CreateTaskScreenViewModel>()
    val formState by viewModel.formState.collectAsState()

    CreateTaskScreenLayout(
        snackBarHostState = snackBarHostState,
        onNavigateToHome = onNavigateToHome,
        saveButton = {
            SaveButton {
                viewModel.viewModelScope.launch {
                    viewModel.onSaveClick()
                    snackBarHostState.showSnackbar("Created!!")
                    onNavigateToHome()
                }
            }
        }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row {
                EditableText(
                    onValidate = { text: String -> validateText(text) },
                    isEditMode = true,
                    content = formState.title,
                    colors = MaterialTheme.colorScheme.editableTitleOutlinedTextFieldColors,
                    onValueChange = viewModel::onTitleValueChange,
                    isSingleLine = true,
                    label = "Title"
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row {
                EditableText(
                    onValidate = { text: String -> validateText(text) },
                    isEditMode = true,
                    content = formState.content,
                    colors = MaterialTheme.colorScheme.editableTitleOutlinedTextFieldColors,
                    onValueChange = viewModel::onContentValueChange,
                    isSingleLine = false,
                    label = "Content"
                )
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row {
                DatePickerDocked("Due Date", viewModel::onDueDateValueChange)
            }
        }
    }
}