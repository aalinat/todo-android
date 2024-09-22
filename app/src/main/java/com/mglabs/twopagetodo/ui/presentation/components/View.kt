package com.mglabs.twopagetodo.ui.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mglabs.twopagetodo.domain.model.TodoTask
import com.mglabs.twopagetodo.ui.presentation.details.DetailsFormState
import com.mglabs.twopagetodo.ui.presentation.utils.toFormattedDate
import com.mglabs.twopagetodo.ui.presentation.utils.validateText

@Composable
fun ItemList(
    items: List<TodoTask>,
    onEdit: (todo: TodoTask) -> Unit,
    onDelete: (todo: TodoTask) -> Unit,
    onFavorite: (todo: TodoTask) -> Unit,
    onUnFavorite: (todo: TodoTask) -> Unit,
) {
    LazyColumn {
        itemsIndexed(items) { _, item ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(text = item.title,
                    Modifier
                        .weight(1f)
                        .clickable { onEdit(item) })
                DeleteAction { onDelete(item) }
                FavoriteAction(item.isFavorite) {
                    when (item.isFavorite) {
                        true -> onUnFavorite(item)
                        false -> onFavorite(item)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemDetailsForm(
    formState: DetailsFormState,
    onContentValueChange: (text: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = toFormattedDate(formState.createdAt),
            color = MaterialTheme.colorScheme.secondary,
        )
        Spacer(modifier = Modifier.padding(10.dp))
        EditableText(
            isEditMode = formState.isEditMode,
            content = formState.content,
            onValueChange = onContentValueChange,
            errorMessage =  formState.titleError
        )
    }
}