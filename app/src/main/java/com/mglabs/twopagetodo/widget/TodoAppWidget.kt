package com.mglabs.twopagetodo.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.LocalGlanceId
import androidx.glance.appwidget.CircularProgressIndicator
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontStyle
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle

class TodoAppWidget: GlanceAppWidget()  {
    override val sizeMode: SizeMode = SizeMode.Exact
    override val stateDefinition = TodoWidgetStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }

    @Composable
    fun Content() {
        val context = LocalContext.current
        val glanceId = LocalGlanceId.current
        val todoState = currentState<TodoState>()
        GlanceTheme {
            Box(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .appWidgetBackground()
                    .background(GlanceTheme.colors.background)
                    .cornerRadius(16.dp)
            ) {
                when (todoState) {
                    TodoState.Loading -> LoadingState()
                    is TodoState.Success -> ShowWidget(todoState.todos)
                }
                LaunchedEffect(Unit){
                    TodosWorker.enqueue(context, glanceId)
                }
            }
        }
    }
    @Composable
    private fun ShowWidget(todos: List<String>) {
        LazyColumn {
            items(items = todos) { item->
                Text(
                    text = item,
                    style = TextStyle(
                        color = GlanceTheme.colors.onSurface,
                        fontSize = 14.sp,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Start
                    ),
                    modifier = GlanceModifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(GlanceTheme.colors.surface)
                )
            }
        }
    }
    @Composable
    private fun LoadingState() {
        CircularProgressIndicator()
    }
}