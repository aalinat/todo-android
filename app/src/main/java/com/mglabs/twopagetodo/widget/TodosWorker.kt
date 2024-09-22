package com.mglabs.twopagetodo.widget

import android.content.Context
import android.util.Log
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class TodosWorker(
    private val context: Context,
    workerParameters: WorkerParameters
)   : CoroutineWorker(context, workerParameters) {

    companion object {
        private const val FORCE_KEY = "force"

        private val uniqueWorkName = TodosWorker::class.java.simpleName

        fun enqueue(context: Context, glanceId: GlanceId, force: Boolean = false) {
            val manager = WorkManager.getInstance(context)
            val requestBuilder = OneTimeWorkRequestBuilder<TodosWorker>().apply {
                addTag(glanceId.toString())
                setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                setInputData(
                    Data.Builder()
                        .putBoolean(FORCE_KEY, force)
                        .build()
                )
            }
            val workPolicy = if (force) {
                ExistingWorkPolicy.REPLACE
            } else {
                ExistingWorkPolicy.KEEP
            }

            manager.enqueueUniqueWork(
                uniqueWorkName,
                workPolicy,
                requestBuilder.build()
            )
        }
    }
    override suspend fun doWork(): Result {
        return try {
            setWidgetState(TodoState.Loading)
            delay(1000)
            setWidgetState(TodoState.Success(listOf("Todo 1", "Todo 2")))
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

