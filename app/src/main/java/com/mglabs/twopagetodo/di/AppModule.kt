package com.mglabs.twopagetodo.di

import com.mglabs.twopagetodo.data.repository.TodoTaskRepositoryImpl
import com.mglabs.twopagetodo.domain.repository.TodoTaskRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoTaskRepository(): TodoTaskRepository {
        return TodoTaskRepositoryImpl()
    }

}