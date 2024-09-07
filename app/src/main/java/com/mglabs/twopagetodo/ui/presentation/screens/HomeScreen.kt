package com.mglabs.twopagetodo.ui.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mglabs.twopagetodo.domain.TodoTask
import com.mglabs.twopagetodo.ui.presentation.components.LoadingContent
import com.mglabs.twopagetodo.ui.presentation.layouts.AppBarLayout
import com.mglabs.twopagetodo.ui.presentation.viewmodels.HomeScreenViewModel
import kotlin.math.roundToInt


@Composable
fun HomeScreen(
    screenModel: HomeScreenViewModel  = hiltViewModel<HomeScreenViewModel>(),
    onNavigateToDetails: (TodoTask) -> Unit
) {

    val state by screenModel.uiState.collectAsState()
    val onEdit: (todo: TodoTask) -> Unit = { todo ->
        onNavigateToDetails(todo)
    }
    val onDelete: (todo: TodoTask) -> Unit = { todo ->
        screenModel.deleteItem(todo.id)
    }
    val onFloatingActionClick: () -> Unit = {
        screenModel.addItem(
            TodoTask(
                (Math.random() * 1000).roundToInt(),
                "Ahmad",
                "title",
                "content"
            )
        )
    }

    AppBarLayout(
        title = "My Todo List",
        floatingButton = { FloatingButton(onFloatingActionClick) },
        content = {
            when (val result = state) {
                is HomeScreenViewModel.State.Loading -> LoadingContent()
                is HomeScreenViewModel.State.Success -> ListItems(
                    result.items,
                    onEdit = onEdit,
                    onDelete = onDelete
                )
            }
        })
}

@Composable
fun ListItems(
    items: List<TodoTask>,
    onEdit: (todo: TodoTask) -> Unit,
    onDelete: (todo: TodoTask) -> Unit
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
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Delete",
                    Modifier.clickable {
                        onDelete(item)
                    })
            }
        }
    }
}

@Composable
fun FloatingButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = {
            onClick()
        },
    ) {
        Icon(Icons.Filled.Add, "Add Item")
    }
}
