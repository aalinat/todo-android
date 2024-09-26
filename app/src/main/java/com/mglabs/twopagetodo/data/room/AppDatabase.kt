package com.mglabs.twopagetodo.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mglabs.twopagetodo.data.room.dao.ProjectDao
import com.mglabs.twopagetodo.data.room.dao.TodoTaskDao
import com.mglabs.twopagetodo.data.room.entities.Project
import com.mglabs.twopagetodo.data.room.entities.TodoTask

@Database(entities = [TodoTask::class, Project::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase  : RoomDatabase() {
    abstract fun todoTaskDao(): TodoTaskDao
    abstract fun projectDao(): ProjectDao

    companion object {
        private const val DATABASE_NAME = "todos"
        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }
        private fun buildDatabase(context: Context): AppDatabase {
            // context.deleteDatabase(DATABASE_NAME)
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }

}