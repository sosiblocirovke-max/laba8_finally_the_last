package com.example.todoapp.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todoapp.model.Task

/**
 * Экран списка задач: секции «главные» и «второстепенные» (всё, что не главное), избранное, поиск.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
    onTaskClick: (taskId: Long) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val vm: TaskListViewModel = hiltViewModel()
    val state by vm.uiState.collectAsStateWithLifecycle()

    val mainTasks = remember(state.tasks) { state.tasks.filter { it.isMainTask } }
    val secondaryTasks = remember(state.tasks) { state.tasks.filter { !it.isMainTask } }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Задачи") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Добавить задачу")
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            OutlinedTextField(
                value = state.query,
                onValueChange = vm::onQueryChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                label = { Text("Поиск по названию") },
                singleLine = true,
            )
            state.error?.let { err ->
                Text(
                    text = err,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                if (mainTasks.isNotEmpty()) {
                    item {
                        SectionHeader(title = "Главные задачи")
                    }
                    items(mainTasks, key = { it.id }) { task ->
                        TaskListRow(
                            task = task,
                            onTaskClick = onTaskClick,
                            onToggleDone = { vm.onToggleDone(it) },
                            onToggleFavorite = { vm.onToggleFavorite(it) },
                            onDelete = { vm.onDelete(it) },
                        )
                    }
                }
                if (secondaryTasks.isNotEmpty()) {
                    item {
                        SectionHeader(title = "Второстепенные задачи")
                    }
                    items(secondaryTasks, key = { it.id }) { task ->
                        TaskListRow(
                            task = task,
                            onTaskClick = onTaskClick,
                            onToggleDone = { vm.onToggleDone(it) },
                            onToggleFavorite = { vm.onToggleFavorite(it) },
                            onDelete = { vm.onDelete(it) },
                        )
                    }
                }
                if (mainTasks.isEmpty() && secondaryTasks.isEmpty()) {
                    item {
                        Text(
                            text = "Пока нет задач — нажмите +",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 24.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(title: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 1.dp,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp),
        )
    }
}

@Composable
private fun TaskListRow(
    task: Task,
    onTaskClick: (Long) -> Unit,
    onToggleDone: (Task) -> Unit,
    onToggleFavorite: (Task) -> Unit,
    onDelete: (Task) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onTaskClick(task.id) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Checkbox(
            checked = task.isDone,
            onCheckedChange = { onToggleDone(task) },
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = task.title, style = MaterialTheme.typography.titleMedium)
            Text(text = task.description, style = MaterialTheme.typography.bodyMedium)
        }
        IconButton(onClick = { onToggleFavorite(task) }) {
            Icon(
                imageVector = if (task.isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                contentDescription = if (task.isFavorite) "Убрать из избранного" else "В избранное",
                tint = if (task.isFavorite) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
            )
        }
        IconButton(onClick = { onDelete(task) }) {
            Icon(Icons.Default.Delete, contentDescription = "Удалить")
        }
    }
}
