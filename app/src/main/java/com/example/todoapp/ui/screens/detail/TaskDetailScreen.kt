package com.example.todoapp.ui.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlinx.coroutines.launch

/**
 * Детальный экран задачи: загрузка, карточка с датами и диалог подтверждения удаления.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    onBack: () -> Unit,
    onEdit: (Long) -> Unit,
    onDeleted: () -> Unit,
    viewModel: TaskDetailViewModel = hiltViewModel(),
) {
    val task by viewModel.uiState.collectAsStateWithLifecycle()
    var showDeleteDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val currentTask = task
    if (showDeleteDialog && currentTask != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Удалить задачу?") },
            text = { Text("Задача будет удалена без возможности восстановления.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        scope.launch {
                            viewModel.onDelete(currentTask).join()
                            onDeleted()
                        }
                    },
                ) {
                    Text("Удалить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Отмена")
                }
            },
        )
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Задача") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Назад",
                        )
                    }
                },
                actions = {
                    currentTask?.let { t ->
                        IconButton(onClick = { viewModel.onToggleFavorite(t) }) {
                            Icon(
                                imageVector = if (t.isFavorite) Icons.Filled.Star else Icons.Outlined.StarOutline,
                                contentDescription = if (t.isFavorite) "Убрать из избранного" else "В избранное",
                                tint = if (t.isFavorite) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                },
                            )
                        }
                        IconButton(onClick = { onEdit(t.id) }) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Изменить",
                            )
                        }
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Удалить",
                            )
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (val t = task) {
                null -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }
                else -> {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = t.title,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                        Row(
                            modifier = Modifier.padding(top = 12.dp, bottom = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            AssistChip(
                                onClick = {},
                                label = {
                                    Text(if (t.isDone) "Выполнено" else "В работе")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = if (t.isDone) {
                                            Icons.Default.CheckCircle
                                        } else {
                                            Icons.Default.Schedule
                                        },
                                        contentDescription = null,
                                    )
                                },
                            )
                            AssistChip(
                                onClick = {},
                                label = {
                                    Text(if (t.isMainTask) "Главная" else "Второстепенная")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = if (t.isMainTask) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                                        contentDescription = null,
                                    )
                                },
                            )
                        }
                        if (t.description.isNotBlank()) {
                            Text(
                                text = t.description,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 8.dp),
                            )
                        }
                        t.dueDate?.let { due ->
                            Text(
                                text = "До: ${formatDate(due)}",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(bottom = 4.dp),
                            )
                        }
                        Text(
                            text = "Создано: ${formatDate(t.createdAt)}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
    }
}

private fun formatDate(epochMillis: Long): String {
    val zoned = Instant.ofEpochMilli(epochMillis).atZone(ZoneId.systemDefault())
    val formatter = DateTimeFormatter.ofPattern("d MMM yyyy, HH:mm", Locale.getDefault())
    return formatter.format(zoned)
}
