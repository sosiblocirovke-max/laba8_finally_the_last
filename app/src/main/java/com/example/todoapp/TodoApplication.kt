package com.example.todoapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/** Корневой класс приложения — включает кодогенерацию Hilt (компонент Singleton). */
@HiltAndroidApp
class TodoApplication : Application()
