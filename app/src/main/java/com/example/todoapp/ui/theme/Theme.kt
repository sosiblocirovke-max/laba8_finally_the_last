package com.example.todoapp.ui.theme

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Корневая тема TodoApp: Material 3 [ColorScheme] и [Typography], опционально dynamic color на Android 12+ (S).
 */
@Composable
fun TodoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> TodoDarkColorScheme
        else -> TodoLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}

/**
 * Набор типовых компонентов для проверки контраста и читаемости в превью.
 */
@Composable
private fun ThemeComponentsSample() {
    var sampleText by remember { mutableStateOf("Поле ввода") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "TodoApp — образец темы",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
        )
        Button(onClick = { }) {
            Text("Кнопка", style = MaterialTheme.typography.labelLarge)
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
        ) {
            Text(
                text = "Карточка на surfaceVariant: проверка onSurfaceVariant.",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        OutlinedTextField(
            value = sampleText,
            onValueChange = { sampleText = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Метка поля") },
            singleLine = true,
        )
        Text(
            text = "Вспомогательный текст (bodySmall)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Preview(name = "TodoApp — светлая тема", showBackground = true)
@Composable
private fun TodoAppThemePreviewLight() {
    TodoAppTheme(darkTheme = false, dynamicColor = false) {
        ThemeComponentsSample()
    }
}

@Preview(
    name = "TodoApp — тёмная тема",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
private fun TodoAppThemePreviewDark() {
    TodoAppTheme(darkTheme = true, dynamicColor = false) {
        ThemeComponentsSample()
    }
}
