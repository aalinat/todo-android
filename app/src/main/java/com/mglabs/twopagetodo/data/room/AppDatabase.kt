package com.mglabs.twopagetodo.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mglabs.twopagetodo.data.room.dao.TodoTaskDao
import com.mglabs.twopagetodo.data.room.entities.TodoTask

@Database(entities = [TodoTask::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase  : RoomDatabase() {
    abstract fun todoTaskDao(): TodoTaskDao

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
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}