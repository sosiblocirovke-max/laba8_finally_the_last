package com.example.todoapp.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapp.ui.screens.detail.TaskDetailScreen
import com.example.todoapp.ui.screens.edit.TaskEditScreen
import com.example.todoapp.ui.screens.list.TaskListScreen

/**
 * Граф NavHost: список → детальный экран → редактор; переходы и pop остаются в хосте, экраны получают лямбды.
 */
@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.List.route,
        modifier = Modifier.fillMaxSize(),
    ) {
        composable(Screen.List.route) {
            TaskListScreen(
                onTaskClick = { id -> navController.navigate(Screen.Detail.create(id)) },
                onAddClick = { navController.navigate(Screen.Edit.create()) },
            )
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("taskId") { type = NavType.LongType },
            ),
        ) {
            TaskDetailScreen(
                onBack = { navController.popBackStack() },
                onEdit = { id -> navController.navigate(Screen.Edit.create(id)) },
                onDeleted = { navController.popBackStack() },
            )
        }
        composable(
            route = Screen.Edit.route,
            arguments = listOf(
                navArgument("taskId") {
                    type = NavType.LongType
                    defaultValue = -1L
                },
            ),
        ) {
            TaskEditScreen(
                onBack = { navController.popBackStack() },
                onSaved = { navController.popBackStack() },
            )
        }
    }
}
