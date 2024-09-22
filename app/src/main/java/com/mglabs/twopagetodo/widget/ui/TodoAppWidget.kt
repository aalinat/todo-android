package com.mglabs.twopagetodo.widget.ui

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import com.mglabs.twopagetodo.widget.actions.fetch.FetchTodoTasksWorker
import com.mglabs.twopagetodo.widget.ui.WorkerParameters.TODO_TASK_ID_KEY
import com.mglabs.twopagetodo.widget.ui.state.TodoWidgetStateDefinition

object WorkerParameters {
    const val TODO_TASK_ID_KEY = "todo_task_id"
    const val TODO_TASK_STATUS_KEY = "todo_task_status"
    const val FORCE_KEY = "force"
}

object ActionParameters {
    val taskIdKey = ActionParameters.Key<Int>(TODO_TASK_ID_KEY)
}

class TodoAppWidget : GlanceAppWidget() {
    override val sizeMode: SizeMode = SizeMode.Exact
    override val stateDefinition = TodoWidgetStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }

    override suspend fun onDelete(context: Context, glanceId: GlanceId) {
        super.onDelete(context, glanceId)
        FetchTodoTasksWorker.cancel(context, glanceId)
    }
}