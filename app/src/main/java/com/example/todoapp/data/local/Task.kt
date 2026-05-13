package com.example.todoapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Строка таблицы `tasks` в Room: хранит поля задачи для офлайн-доступа.
 * Для дат вне Long (например [java.time.LocalDateTime]) используйте [Converters], зарегистрированные на [TodoDatabase].
 */
@Entity(tableName = "tasks")
data class Task(
    /** Идентификатор записи в SQLite; `0` означает новую строку до первого insert (автоинкремент). */
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    /** Краткий заголовок задачи, отображается в списках и уведомлениях. */
    val title: String,
    /** Подробное описание; может быть пустым, если достаточно заголовка. */
    val description: String = "",
    /** Признак выполнения: `true`, если задача закрыта. */
    val isDone: Boolean = false,
    /** Дедлайн в миллисекундах с эпохи UTC; `null`, если срок не задан. */
    val dueDate: Long? = null,
    /** Момент создания записи (миллисекунды UTC); по умолчанию — текущее время при создании объекта в коде. */
    val createdAt: Long = System.currentTimeMillis(),
    /** `true` — главная задача; `false` — второстепенная (все такие показываются отдельным разделом в списке). */
    val isMainTask: Boolean = true,
    /** Избранное: выделение звёздочкой в списке и на детальном экране. */
    val isFavorite: Boolean = false,
)
