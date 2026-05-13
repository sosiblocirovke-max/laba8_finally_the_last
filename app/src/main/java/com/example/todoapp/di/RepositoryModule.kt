package com.example.todoapp.di

import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.data.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Связывает контракты репозиториев с реализациями. Здесь используется [Binds]: см. пояснение в документации к проекту / ответе ассистента.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository
}
