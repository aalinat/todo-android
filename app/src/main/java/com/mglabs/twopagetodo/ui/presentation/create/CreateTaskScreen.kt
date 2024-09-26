package com.mglabs.twopagetodo.ui.presentation.create

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.mglabs.twopagetodo.ui.presentation.components.CreateTaskScreenLayout
import com.mglabs.twopagetodo.ui.presentation.components.DatePickerDocked
import com.mglabs.twopagetodo.ui.presentation.components.EditableText
import com.mglabs.twopagetodo.ui.presentation.components.LoadingContent
import com.mglabs.twopagetodo.ui.presentation.components.SaveButton
import com.mglabs.twopagetodo.ui.theme.editableOutlinedTextFieldBasicColors
import kotlinx.coroutines.launch

@Composable
fun CreateTaskScreen(snackBarHostState: SnackbarHostState, onNavigateToHome: () -> Unit) {
    val viewModel: CreateTaskScreenViewModel = hiltViewModel<CreateTaskScreenViewModel>()
    val formState by viewModel.formState.collectAsState()
    var selectedIndex by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    when (val result = uiState) {
        is CreateTaskScreenViewModel.State.Loading -> LoadingContent()
        is CreateTaskScreenViewModel.State.Success ->
            CreateTaskScreenLayout(
                snackBarHostState = snackBarHostState,
                onNavigateToHome = onNavigateToHome,
                saveButton = {
                    SaveButton {
                        viewModel.viewModelScope.launch {
                            if (viewModel.onSaveClick()) {
                                onNavigateToHome()
                                snackBarHostState.showSnackbar("Task Created")
                            } else {
                                snackBarHostState.showSnackbar(
                                    message = formState.getErrors()
                                )
                            }
                        }
                    }
                }
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row {
                        EditableText(
                            isEditMode = true,
                            content = formState.title,
                            colors = MaterialTheme.colorScheme.editableOutlinedTextFieldBasicColors,
                            onValueChange = viewModel::onTitleValueChange,
                            isSingleLine = true,
                            label = "Title",
                            errorMessage = formState.titleError

                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row {
                        EditableText(
                            isEditMode = true,
                            content = formState.content,
                            colors = MaterialTheme.colorScheme.editableOutlinedTextFieldBasicColors,
                            onValueChange = viewModel::onContentValueChange,
                            isSingleLine = false,
                            label = "Description",
                            errorMessage = formState.contentError
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentSize(Alignment.TopStart)
                        ) {
                            Text(
                                text = result.projects.first().name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable(onClick = { expanded = true })
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                result.projects.forEach { project ->
                                    DropdownMenuItem(
                                        text = { Text(text = project.name) },
                                        onClick = {
                                            selectedIndex = project.id
                                            expanded = false
                                        })
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row {
                        DatePickerDocked(
                            "Due Date",
                            formState.dueDate,
                            viewModel::onDueDateValueChange
                        )
                    }
                }
            }

    }
}