package com.mglabs.twopagetodo.widget.ui.state

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.mglabs.twopagetodo.widget.actions.fetch.FetchTodoTasksWorker
import com.mglabs.twopagetodo.widget.ui.TodoAppWidget

class TodoAppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = TodoAppWidget()

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        Log.i("TodoAppWidgetReceiver", "TodoAppWidgetReceiver")
    }
}