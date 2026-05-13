package com.example.todoapp.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel экрана деталей: [taskId] читается из [SavedStateHandle] (ключ совпадает с именем аргумента в `navArgument`),
 * UI подписывается на [Task] из репозитория; переключение «выполнено», «избранное» и удаление уходят в [TaskRepository] в корутине.
 */
@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TaskRepository,
) : ViewModel() {

    private val taskId: Long = checkNotNull(savedStateHandle["taskId"])

    val uiState: StateFlow<Task?> =
        repository.getById(taskId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null,
        )

    fun onToggleDone(task: Task) = viewModelScope.launch {
        repository.save(task.copy(isDone = !task.isDone))
    }

    fun onDelete(task: Task) = viewModelScope.launch {
        repository.delete(task)
    }

    fun onToggleFavorite(task: Task) = viewModelScope.launch {
        repository.save(task.copy(isFavorite = !task.isFavorite))
    }
}
