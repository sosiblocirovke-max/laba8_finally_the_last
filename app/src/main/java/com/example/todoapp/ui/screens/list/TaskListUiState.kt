package com.example.todoapp.ui.screens.list

import com.example.todoapp.model.Task
import com.example.todoapp.viewmodel.TodoUiState

/**
 * Единое UI-состояние экрана списка: один [TaskListUiState] на экран упрощает подписку в Compose и исключает
 * рассинхрон между списком, строкой поиска, загрузкой и ошибкой.
 *
 * @property tasks Задачи для отображения (уже отфильтрованы репозиторием по [query]).
 * @property isLoading Признак первичной/фоновой загрузки (до первого успешного значения — `true` по умолчанию).
 * @property error Текст ошибки загрузки или мутации; `null`, если ошибок нет.
 * @property query Текущая строка поиска (синхронизирована с [TaskListViewModel.onQueryChange]).
 */
data class TaskListUiState(
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val query: String = "",
) : TodoUiState
