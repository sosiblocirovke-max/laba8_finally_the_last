package com.example.todoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Корневой [Application] приложения: аннотация [HiltAndroidApp] включает генерацию компонента приложения
 * и графа зависимостей Hilt (Singleton, модули [com.example.todoapp.di] и т.д.).
 */
@HiltAndroidApp
class TodoApplication : Application()
