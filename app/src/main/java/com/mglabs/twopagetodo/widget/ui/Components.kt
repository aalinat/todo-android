package com.mglabs.twopagetodo.widget.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.action.actionParametersOf
import androidx.glance.appwidget.CheckBox
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.components.CircleIconButton
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontStyle
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.mglabs.twopagetodo.R
import com.mglabs.twopagetodo.widget.actions.fetch.RefreshAction
import com.mglabs.twopagetodo.widget.actions.update.UpdateTodoTaskStatusAction
import com.mglabs.twopagetodo.widget.ui.ActionParameters.taskIdKey
import com.mglabs.twopagetodo.widget.ui.state.TodoState
import com.mglabs.twopagetodo.widget.ui.state.WidgetItem

@Composable
fun Content() {
    val todoState = currentState<TodoState>()
    GlanceTheme {
        Scaffold(
            titleBar = { WidgetTopBar() },
            modifier = GlanceModifier
                .fillMaxSize()
                .appWidgetBackground()
                .background(GlanceTheme.colors.background)
                .cornerRadius(16.dp)
        ) {

            when (todoState) {
                TodoState.Loading -> LoadingState()
                is TodoState.Success -> {
                    if (todoState.todos.isEmpty()) {
                        EmptyWidget()
                    } else {
                        ShowWidget(todoState.todos)
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyWidget() {
    Row(modifier = GlanceModifier.fillMaxWidth().padding(8.dp)) {
        Text(text = "All Done!!")
    }
}

@Composable
private fun ShowWidget(todos: List<WidgetItem>) {
    LazyColumn {
        items(items = todos) { item ->
            Row(modifier = GlanceModifier.fillMaxWidth().padding(4.dp)) {
                CheckBox(
                    checked = false, onCheckedChange = actionRunCallback<UpdateTodoTaskStatusAction>(
                        actionParametersOf(
                            taskIdKey to item.id
                        )
                    )
                )
                Text(
                    text = item.title,
                    style = TextStyle(
                        color = GlanceTheme.colors.onSurface,
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Start
                    ),
                    modifier = GlanceModifier
                        .padding(8.dp)
                )
            }
        }
    }
}

@Composable
private fun LoadingState() {
    CircularProgressIndicator()
}

@Composable
private fun WidgetTopBar() {
    TitleBar(
        startIcon = ImageProvider(R.drawable.ic_launcher_foreground),
        title = "Todo App Widget",
        modifier = GlanceModifier.fillMaxWidth().background(Color.Cyan),
        actions = {
            CircleIconButton(contentDescription = "Refresh Widget",
                imageProvider = ImageProvider(R.drawable.baseline_refresh_24),
                backgroundColor = ColorProvider(Color.Cyan),
                onClick = actionRunCallback<RefreshAction>()
            )
        }
    )
}


