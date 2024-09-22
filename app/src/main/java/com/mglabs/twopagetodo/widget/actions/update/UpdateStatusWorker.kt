package com.mglabs.twopagetodo.widget.actions.update


import android.content.Context
import androidx.glance.GlanceId
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import com.mglabs.twopagetodo.shared.TaskStatus
import com.mglabs.twopagetodo.widget.ui.WorkerParameters.FORCE_KEY
import com.mglabs.twopagetodo.widget.ui.WorkerParameters.TODO_TASK_ID_KEY
import com.mglabs.twopagetodo.widget.ui.WorkerParameters.TODO_TASK_STATUS_KEY
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UpdateStatusWorker @AssistedInject constructor(
    private val todoTaskRepository: TodoTaskRepository,
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {
    companion object {
        private val uniqueWorkName = UpdateStatusWorker::class.java.simpleName

        fun enqueue(context: Context, glanceId: GlanceId, todoTaskId: Int, taskStatus: TaskStatus, force: Boolean = false) {
            val manager = WorkManager.getInstance(context)
            val requestBuilder = OneTimeWorkRequestBuilder<UpdateStatusWorker>().apply {
                addTag(glanceId.toString())
                setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                setInputData(
                    Data.Builder()
                        .putBoolean(FORCE_KEY, force)
                        .putInt(TODO_TASK_ID_KEY, todoTaskId)
                        .putString(TODO_TASK_STATUS_KEY, taskStatus.toString())
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
        val taskStatusAsString = inputData.getString(TODO_TASK_STATUS_KEY)
        if (taskStatusAsString != null) {
            val taskId = inputData.getInt(TODO_TASK_ID_KEY, -1)
            val taskStatus = TaskStatus.valueOf(taskStatusAsString)
            val todo = todoTaskRepository.findById(taskId)
            todo?.let {
                it.status = taskStatus
                todoTaskRepository.update(it)
            }
            return Result.success()
        }
        return Result.failure()

    }
}