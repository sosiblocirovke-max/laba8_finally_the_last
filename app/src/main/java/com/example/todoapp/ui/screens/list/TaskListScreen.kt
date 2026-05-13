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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

/**
 * Экран списка задач: подписан на [TaskListViewModel.uiState], поиск и действия идут во ViewModel.
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
                items(state.tasks, key = { it.id }) { task ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onTaskClick(task.id) },
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Checkbox(
                            checked = task.isDone,
                            onCheckedChange = { vm.onToggleDone(task) },
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = task.title, style = MaterialTheme.typography.titleMedium)
                            Text(text = task.description, style = MaterialTheme.typography.bodyMedium)
                        }
                        IconButton(onClick = { vm.onDelete(task) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Удалить")
                        }
                    }
                }
            }
        }
    }
}
