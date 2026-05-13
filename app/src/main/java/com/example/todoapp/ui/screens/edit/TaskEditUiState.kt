package com.example.todoapp.ui.screens.edit

/**
 * Состояние формы редактирования: поля черновика, флаги сохранения и текст ошибки валидации/сети.
 */
data class TaskEditUiState(
    val title: String = "",
    val description: String = "",
    val dueDate: Long? = null,
    val isDone: Boolean = false,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null,
)
