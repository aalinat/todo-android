package com.mglabs.twopagetodo.widget.actions.update

import android.content.Context
import androidx.glance.GlanceId
import androidx.glance.action.ActionParameters
import androidx.glance.appwidget.action.ActionCallback
import com.mglabs.twopagetodo.shared.TaskStatus
import com.mglabs.twopagetodo.widget.ui.ActionParameters.taskIdKey
import com.mglabs.twopagetodo.widget.ui.TodoAppWidget

class UpdateTodoTaskStatusAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        TodoAppWidget().update(context, glanceId)
        val todoTaskId = parameters[taskIdKey]
        val taskStatus = TaskStatus.DONE
        todoTaskId?.let {
            UpdateStatusWorker.enqueue(
                context,
                glanceId,
                force = true,
                todoTaskId = it,
                taskStatus = taskStatus
            )
        }
    }
}