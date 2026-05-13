package com.example.todoapp.data.repository

import com.example.todoapp.data.local.Task as LocalTask
import com.example.todoapp.data.local.TaskDao
import com.example.todoapp.model.Task
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Реализация [TaskRepository] поверх Room: переводит [LocalTask] ↔ доменный [Task] и делегирует вызовы [dao].
 * Верхний слой (ViewModel) видит только контракт репозитория и не знает о SQLite и аннотациях Room.
 */
@Singleton
class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao,
) : TaskRepository {

    override fun getAll(): Flow<List<Task>> =
        dao.getAll().map { list -> list.map { it.toDomain() } }

    override fun getById(id: Long): Flow<Task?> =
        dao.getById(id).map { entity -> entity?.toDomain() }

    override suspend fun save(task: Task): Long {
        val entity = task.toEntity()
        return if (entity.id == 0L) {
            dao.insert(entity)
        } else {
            dao.update(entity)
            task.id
        }
    }

    override suspend fun delete(task: Task) {
        dao.delete(task.toEntity())
    }

    override suspend fun deleteById(id: Long) {
        dao.deleteById(id)
    }

    override fun search(query: String): Flow<List<Task>> =
        dao.search(query).map { list -> list.map { it.toDomain() } }

    private fun LocalTask.toDomain(): Task = Task(
        id = id,
        title = title,
        description = description,
        isDone = isDone,
        dueDate = dueDate,
        createdAt = createdAt,
    )

    private fun Task.toEntity(): LocalTask = LocalTask(
        id = id,
        title = title,
        description = description,
        isDone = isDone,
        dueDate = dueDate,
        createdAt = createdAt,
    )
}
