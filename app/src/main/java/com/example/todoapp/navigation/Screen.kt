package com.example.todoapp.navigation

/**
 * Маршруты графа навигации: стартовый список, деталь и редактор с плейсхолдером [taskId].
 */
sealed class Screen(val route: String) {

    object List : Screen("list")

    object Detail : Screen("detail/{taskId}") {
        fun create(id: Long): String = "detail/$id"
    }

    object Edit : Screen("edit/{taskId}") {
        fun create(id: Long = -1L): String = "edit/$id"
    }
}
