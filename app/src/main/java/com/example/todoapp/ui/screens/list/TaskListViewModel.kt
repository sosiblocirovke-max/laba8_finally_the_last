package com.example.todoapp.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel списка задач: держит поисковый запрос в [MutableStateFlow], строит единый [TaskListUiState] из репозитория
 * и обрабатывает действия пользователя (поиск, выполнение, удаление) с обновлением [TaskListUiState.error] при сбоях.
 */
@HiltViewModel
class TaskListViewModel @Inject constructor(
    private val repository: TaskRepository,
) : ViewModel() {

    private val _query = MutableStateFlow("")

    /** Ошибки операций [onToggleDone], [onDelete], [onToggleFavorite]; комбинируется с ошибками потока данных. */
    private val _mutationError = MutableStateFlow<String?>(null)

    private val tasksFromRepo: Flow<Triple<String, List<Task>, String?>> =
        _query.flatMapLatest { q ->
            val source = if (q.isBlank()) repository.getAll() else repository.search(q)
            source
                .map { list -> Triple(q, list, null as String?) }
                .catch { e ->
                    emit(Triple(q, emptyList(), e.message))
                }
        }

    val uiState: StateFlow<TaskListUiState> =
        combine(tasksFromRepo, _mutationError) { triple, mutationErr ->
            val (q, tasks, flowErr) = triple
            TaskListUiState(
                tasks = tasks,
                isLoading = false,
                error = mutationErr ?: flowErr,
                query = q,
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TaskListUiState(),
            )

    /** Обновляет поисковый запрос и сбрасывает ошибку мутации. */
    fun onQueryChange(q: String) {
        _mutationError.value = null
        _query.value = q
    }

    /** Переключает флаг выполнения задачи и сохраняет через репозиторий. */
    fun onToggleDone(task: Task) = viewModelScope.launch {
        try {
            repository.save(task.copy(isDone = !task.isDone))
            _mutationError.value = null
        } catch (e: Exception) {
            _mutationError.value = e.message ?: e.toString()
        }
    }

    /** Удаляет задачу; при ошибке выставляет [TaskListUiState.error]. */
    fun onDelete(task: Task) = viewModelScope.launch {
        try {
            repository.delete(task)
            _mutationError.value = null
        } catch (e: Exception) {
            _mutationError.value = e.message ?: e.toString()
        }
    }

    /** Переключает признак «избранное». */
    fun onToggleFavorite(task: Task) = viewModelScope.launch {
        try {
            repository.save(task.copy(isFavorite = !task.isFavorite))
            _mutationError.value = null
        } catch (e: Exception) {
            _mutationError.value = e.message ?: e.toString()
        }
    }
}
