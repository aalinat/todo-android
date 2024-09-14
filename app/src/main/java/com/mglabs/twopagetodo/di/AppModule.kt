package com.mglabs.twopagetodo.di

import android.content.Context
import com.mglabs.twopagetodo.data.repository.LocalTodoTaskRepositoryImpl
import com.mglabs.twopagetodo.data.repository.RoomTodoTaskRepositoryImpl
import com.mglabs.twopagetodo.data.room.AppDatabase
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import com.mglabs.twopagetodo.shared.Config
import com.mglabs.twopagetodo.shared.DataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoTaskRepository(@ApplicationContext context: Context): TodoTaskRepository {
        return when(Config.DATASOURCE) {
            DataSource.Static -> LocalTodoTaskRepositoryImpl()
            DataSource.Room -> RoomTodoTaskRepositoryImpl(AppDatabase.getInstance(context).todoTaskDao())
        }
    }
}