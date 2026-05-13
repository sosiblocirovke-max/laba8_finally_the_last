package com.example.todoapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Общие привязки уровня приложения (синглтоны, не попавшие в [DatabaseModule] / [RepositoryModule]).
 * Добавляйте сюда провайдеры инфраструктуры по мере роста проекта.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /**
     * Пример простого синглтона в графе: идентификатор пакета (полезно для логов/аналитики).
     * Замените или дополните реальными зависимостями (OkHttp, Json и т.д.).
     */
    @Provides
    fun provideApplicationPackageName(@ApplicationContext context: Context): String =
        context.packageName
}
