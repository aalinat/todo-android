package com.mglabs.twopagetodo.di

import android.content.Context
import com.mglabs.twopagetodo.data.room.AppDatabase
import com.mglabs.twopagetodo.data.room.dao.TodoTaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RoomDatabaseModule {
    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }
    @Provides
    fun provideTodoTaskDao(appDatabase: AppDatabase): TodoTaskDao {
        return appDatabase.todoTaskDao()
    }
}