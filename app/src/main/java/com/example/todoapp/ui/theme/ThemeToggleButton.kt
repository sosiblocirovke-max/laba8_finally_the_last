package com.example.todoapp.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Кнопка переключения светлой / тёмной темы: не меняет системные настройки, только локальное состояние приложения.
 */
@Composable
fun ThemeToggleButton(
    isDark: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledTonalIconButton(
        onClick = onToggle,
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
    ) {
        Icon(
            imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
            contentDescription = if (isDark) "Включить светлую тему" else "Включить тёмную тему",
        )
    }
}
