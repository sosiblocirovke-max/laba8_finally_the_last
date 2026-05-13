package com.example.todoapp.data.repository

import com.example.todoapp.model.Task
import kotlinx.coroutines.flow.Flow

/**
 * Контракт доступа к задачам для слоя представления (ViewModel): описывает *что* нужно приложению,
 * но не *как* это хранится. Реализация скрывает Room ([com.example.todoapp.data.local.TaskDao]) и сущности БД,
 * чтобы ViewModel не зависели от источника данных и их было проще подменять в тестах.
 */
interface TaskRepository {

    /** Поток всех задач в порядке, заданном DAO (выполненность, дедлайн, дата создания). */
    fun getAll(): Flow<List<Task>>

    /** Реактивная задача по [id] или `null`, если запись отсутствует. */
    fun getById(id: Long): Flow<Task?>

    /**
     * Создание или обновление: при [Task.id] == `0` выполняется вставка и возвращается сгенерированный id;
     * иначе — обновление существующей строки и возвращается тот же [Task.id].
     */
    suspend fun save(task: Task): Long

    /** Удаление строки, соответствующей [task]. */
    suspend fun delete(task: Task)

    /** Удаление по первичному ключу без загрузки полной модели. */
    suspend fun deleteById(id: Long)

    /** Поток задач, у которых [Task.title] содержит подстроку [query] (см. SQL LIKE в DAO). */
    fun search(query: String): Flow<List<Task>>
}
