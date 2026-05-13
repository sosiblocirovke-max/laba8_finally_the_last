package com.example.todoapp.model

/**
 * Доменная модель задачи: независима от Room, используется во ViewModel и UI.
 * Соответствует полям таблицы `tasks`, маппинг из [com.example.todoapp.data.local.Task] выполняется в репозитории.
 */
data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val isDone: Boolean,
    val dueDate: Long?,
    val createdAt: Long,
    /** Главная (`true`) или второстепенная (`false`) — не попадает в блок «Главные задачи» в списке. */
    val isMainTask: Boolean = true,
    /** Пометка «избранное» (звезда). */
    val isFavorite: Boolean = false,
)
