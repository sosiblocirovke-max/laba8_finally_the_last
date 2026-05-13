package com.example.todoapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Доступ к таблице `tasks`: только SQL и сигнатуры для Room, без доменной логики.
 */
@Dao
interface TaskDao {

    /**
     * Поток всех задач: сначала невыполненные, внутри группы — по растущему дедлайну (null раньше по правилам SQLite),
     * при равенстве — новее по [Task.createdAt].
     */
    @Query("SELECT * FROM tasks ORDER BY isDone ASC, dueDate ASC, createdAt DESC")
    fun getAll(): Flow<List<Task>>

    /**
     * Реактивная выборка одной строки по [id]; эмитит `null`, если задача удалена или ещё не существует.
     */
    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getById(id: Long): Flow<Task?>

    /**
     * Вставка строки; при конфликте по первичному ключу — замена. Возвращает [androidx.room.RoomDatabase.insert] rowId / сгенерированный id.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long

    /**
     * Обновление существующей строки по первичному ключу [Task.id].
     */
    @Update
    suspend fun update(task: Task)

    /**
     * Удаление конкретной строки по совпадению первичного ключа и полей сущности.
     */
    @Delete
    suspend fun delete(task: Task)

    /**
     * Удаление по идентификатору без загрузки полной сущности в память.
     */
    @Query("DELETE FROM tasks WHERE id = :id")
    suspend fun deleteById(id: Long)

    /**
     * Поиск по подстроке в [Task.title] (регистрозависимое сравнение LIKE в SQLite).
     * Параметр [query] — фрагмент без `%`; шаблон формируется в SQL.
     */
    @Query(
        """
        SELECT * FROM tasks
        WHERE title LIKE '%' || :query || '%'
        ORDER BY isDone ASC, dueDate ASC, createdAt DESC
        """,
    )
    fun search(query: String): Flow<List<Task>>
}
