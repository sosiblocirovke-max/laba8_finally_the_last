package com.example.todoapp.ui.screens.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.repository.TaskRepository
import com.example.todoapp.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel экрана создания/редактирования: при [taskId] == `-1` создаётся новая задача, иначе в [init] подгружается существующая.
 * Наружу отдаётся только [uiState] как [StateFlow] через [asStateFlow].
 */
@HiltViewModel
class TaskEditViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: TaskRepository,
) : ViewModel() {

    val taskId: Long = savedStateHandle["taskId"] ?: -1L

    private val _state = MutableStateFlow(TaskEditUiState())
    val uiState: StateFlow<TaskEditUiState> = _state.asStateFlow()

    /** [Task.createdAt] для вставки/обновления доменной модели (в UI не показывается). */
    private var persistedCreatedAt: Long = System.currentTimeMillis()

    init {
        if (taskId != -1L) {
            viewModelScope.launch {
                val task = repository.getById(taskId).first()
                if (task != null) {
                    persistedCreatedAt = task.createdAt
                    _state.update {
                        TaskEditUiState(
                            title = task.title,
                            description = task.description,
                            dueDate = task.dueDate,
                            isDone = task.isDone,
                        )
                    }
                }
            }
        }
    }

    fun onTitleChange(s: String) = _state.update { it.copy(title = s, error = null) }

    fun onDescriptionChange(s: String) = _state.update { it.copy(description = s, error = null) }

    fun onDueDateChange(ts: Long?) = _state.update { it.copy(dueDate = ts, error = null) }

    fun onIsDoneChange(b: Boolean) = _state.update { it.copy(isDone = b, error = null) }

    fun save() = viewModelScope.launch {
        val s = _state.value
        if (s.title.isBlank()) {
            _state.update { it.copy(error = "Заголовок обязателен") }
            return@launch
        }
        _state.update { it.copy(isSaving = true, error = null) }
        try {
            val task = Task(
                id = if (taskId == -1L) 0 else taskId,
                title = s.title,
                description = s.description,
                isDone = s.isDone,
                dueDate = s.dueDate,
                createdAt = persistedCreatedAt,
            )
            repository.save(task)
            _state.update { it.copy(isSaved = true, isSaving = false) }
        } catch (e: Exception) {
            _state.update {
                it.copy(
                    isSaving = false,
                    error = e.message ?: e.toString(),
                )
            }
        }
    }
}
