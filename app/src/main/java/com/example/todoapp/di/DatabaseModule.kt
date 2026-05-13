package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.local.TaskDao
import com.example.todoapp.data.local.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Модуль Hilt для Room: создаёт единственный экземпляр [TodoDatabase] и [TaskDao] в области [SingletonComponent].
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): TodoDatabase =
        Room.databaseBuilder(ctx, TodoDatabase::class.java, TodoDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideTaskDao(db: TodoDatabase): TaskDao = db.taskDao()
}
