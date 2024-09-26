package com.mglabs.twopagetodo.di

import android.content.Context
import com.mglabs.twopagetodo.data.repository.local.TodoTaskRepositoryImpl
import com.mglabs.twopagetodo.data.repository.room.TodoTaskRepositoryImpl as RoomTodoTaskRepositoryImpl
import com.mglabs.twopagetodo.data.repository.local.ProjectRepositoryImpl
import com.mglabs.twopagetodo.data.repository.room.ProjectRepositoryImpl as RoomProjectRepositoryImpl
import com.mglabs.twopagetodo.data.room.AppDatabase
import com.mglabs.twopagetodo.domain.repository.ProjectRepository
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
            DataSource.Static -> TodoTaskRepositoryImpl()
            DataSource.Room -> RoomTodoTaskRepositoryImpl(
                AppDatabase.getInstance(context).todoTaskDao()
            )
        }
    }

    @Provides
    @Singleton
    fun provideProjectsRepository(@ApplicationContext context: Context): ProjectRepository {
        return when(Config.DATASOURCE) {
            DataSource.Static -> ProjectRepositoryImpl()
            DataSource.Room -> RoomProjectRepositoryImpl(
                AppDatabase.getInstance(context).projectDao()
            )
        }
    }
}