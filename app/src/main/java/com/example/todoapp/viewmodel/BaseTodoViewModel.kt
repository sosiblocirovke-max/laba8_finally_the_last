package com.example.todoapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

/**
 * Необязательная база для ViewModel с одним потоком состояния; экраны могут наследовать напрямую [ViewModel].
 * Подчёркивает MVVM-контракт: UI только подписывается на [uiState] и шлёт намерения (intent) методами.
 */
abstract class BaseTodoViewModel<State : TodoUiState> : ViewModel() {
    abstract val uiState: StateFlow<State>
}
