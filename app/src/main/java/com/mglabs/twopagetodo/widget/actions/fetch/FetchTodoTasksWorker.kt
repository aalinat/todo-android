package com.mglabs.twopagetodo.widget.actions.fetch

import android.content.Context
import android.util.Log
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import com.mglabs.twopagetodo.shared.TaskStatus
import com.mglabs.twopagetodo.widget.ui.TodoAppWidget
import com.mglabs.twopagetodo.widget.ui.state.TodoState
import com.mglabs.twopagetodo.widget.ui.state.TodoWidgetStateDefinition
import com.mglabs.twopagetodo.widget.ui.state.WidgetItem
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.Duration

@HiltWorker
class FetchTodoTasksWorker @AssistedInject constructor(
    private val todoTaskRepository: TodoTaskRepository,
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    companion object {
        private val uniqueWorkName = FetchTodoTasksWorker::class.java.simpleName

        fun enqueue(context: Context, force: Boolean = false) {
            val manager = WorkManager.getInstance(context)
            val requestBuilder = PeriodicWorkRequestBuilder<FetchTodoTasksWorker>(
                Duration.ofMinutes(30)
            )
            val workPolicy = if (force) {
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE
            } else {
                ExistingPeriodicWorkPolicy.KEEP
            }

            manager.enqueueUniquePeriodicWork(
                uniqueWorkName,
                workPolicy,
                requestBuilder.build()
            )
        }

        fun cancel(context: Context, glanceId: GlanceId) {
            WorkManager.getInstance(context).cancelAllWorkByTag(glanceId.toString())
        }
    }

    override suspend fun doWork(): Result {
        return try {
            setWidgetState(TodoState.Loading)
            todoTaskRepository.findAll().collect { todos ->
                val items =
                    todos.filter { !it.isDeleted && (it.status == TaskStatus.TODO || it.status == TaskStatus.IN_PROGRESS) }
                        .map { WidgetItem(it.id, it.title, it.isFavorite, it.status) }

                setWidgetState(TodoState.Success(items))
            }
            Result.success()
        } catch (e: Exception) {
            Log.e(uniqueWorkName, "Error while loading todos", e)
            if (runAttemptCount < 10) {
                Result.retry()
            } else {
                Result.failure()
            }
        }
    }

    private suspend fun setWidgetState(newState: TodoState) {
        val manager = GlanceAppWidgetManager(context)
        val glanceIds = manager.getGlanceIds(TodoAppWidget::class.java)
        glanceIds.forEach { glanceId ->
            updateAppWidgetState(
                context = context,
                definition = TodoWidgetStateDefinition,
                glanceId = glanceId,
                updateState = { newState }
            )
        }
        TodoAppWidget().updateAll(context)
    }
}

