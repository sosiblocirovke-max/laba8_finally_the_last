package com.example.todoapp.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

/**
 * Базовые оттенки палитры: тёплый сине-зелёный (teal / sea glass) с мягким уходом в лазурь.
 * Светлая и тёмная [androidx.compose.material3.ColorScheme] собраны из семантических ролей MD3.
 */

// ——— Светлая схема ———
private val LightPrimary = Color(0xFF0F5C52)
private val LightOnPrimary = Color(0xFFFFFFFF)
private val LightPrimaryContainer = Color(0xFFA8EDE0)
private val LightOnPrimaryContainer = Color(0xFF002019)

private val LightSecondary = Color(0xFF3D5F73)
private val LightOnSecondary = Color(0xFFFFFFFF)
private val LightSecondaryContainer = Color(0xFFC8E7FF)
private val LightOnSecondaryContainer = Color(0xFF001E2F)

private val LightTertiary = Color(0xFF3E5A7A)
private val LightOnTertiary = Color(0xFFFFFFFF)
private val LightTertiaryContainer = Color(0xFFD2E4FF)
private val LightOnTertiaryContainer = Color(0xFF061B34)

private val LightBackground = Color(0xFFF7FBF9)
private val LightOnBackground = Color(0xFF191C1C)

private val LightSurface = Color(0xFFF7FBF9)
private val LightOnSurface = Color(0xFF191C1C)
private val LightSurfaceVariant = Color(0xFFDAE5E1)
private val LightOnSurfaceVariant = Color(0xFF3F4946)

private val LightError = Color(0xFFBA1A1A)
private val LightOnError = Color(0xFFFFFFFF)
private val LightErrorContainer = Color(0xFFFFDAD6)
private val LightOnErrorContainer = Color(0xFF410002)

private val LightOutline = Color(0xFF6F7976)
private val LightOutlineVariant = Color(0xFFBFC9C6)

// ——— Тёмная схема ———
private val DarkPrimary = Color(0xFF8CD8CC)
private val DarkOnPrimary = Color(0xFF00382F)
private val DarkPrimaryContainer = Color(0xFF005045)
private val DarkOnPrimaryContainer = Color(0xFFA8EDE0)

private val DarkSecondary = Color(0xFFA8CCF0)
private val DarkOnSecondary = Color(0xFF0F344C)
private val DarkSecondaryContainer = Color(0xFF284B64)
private val DarkOnSecondaryContainer = Color(0xFFC8E7FF)

private val DarkTertiary = Color(0xFFB7C9EA)
private val DarkOnTertiary = Color(0xFF213148)
private val DarkTertiaryContainer = Color(0xFF38475F)
private val DarkOnTertiaryContainer = Color(0xFFD2E4FF)

private val DarkBackground = Color(0xFF0F1413)
private val DarkOnBackground = Color(0xFFE1E3E1)

private val DarkSurface = Color(0xFF0F1413)
private val DarkOnSurface = Color(0xFFE1E3E1)
private val DarkSurfaceVariant = Color(0xFF3F4946)
private val DarkOnSurfaceVariant = Color(0xFFBFC9C6)

private val DarkError = Color(0xFFFFB4AB)
private val DarkOnError = Color(0xFF690005)
private val DarkErrorContainer = Color(0xFF93000A)
private val DarkOnErrorContainer = Color(0xFFFFDAD6)

private val DarkOutline = Color(0xFF899390)
private val DarkOutlineVariant = Color(0xFF3F4946)

/**
 * Полная светлая цветовая схема Material 3 для TodoApp (fallback без dynamic color).
 */
val TodoLightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightPrimaryContainer,
    onPrimaryContainer = LightOnPrimaryContainer,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSecondaryContainer,
    onSecondaryContainer = LightOnSecondaryContainer,
    tertiary = LightTertiary,
    onTertiary = LightOnTertiary,
    tertiaryContainer = LightTertiaryContainer,
    onTertiaryContainer = LightOnTertiaryContainer,
    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightOnSurfaceVariant,
    error = LightError,
    onError = LightOnError,
    errorContainer = LightErrorContainer,
    onErrorContainer = LightOnErrorContainer,
    outline = LightOutline,
    outlineVariant = LightOutlineVariant,
)

/**
 * Полная тёмная цветовая схема Material 3 для TodoApp (fallback без dynamic color).
 */
val TodoDarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    onPrimary = DarkOnPrimary,
    primaryContainer = DarkPrimaryContainer,
    onPrimaryContainer = DarkOnPrimaryContainer,
    secondary = DarkSecondary,
    onSecondary = DarkOnSecondary,
    secondaryContainer = DarkSecondaryContainer,
    onSecondaryContainer = DarkOnSecondaryContainer,
    tertiary = DarkTertiary,
    onTertiary = DarkOnTertiary,
    tertiaryContainer = DarkTertiaryContainer,
    onTertiaryContainer = DarkOnTertiaryContainer,
    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = DarkOnSurfaceVariant,
    error = DarkError,
    onError = DarkOnError,
    errorContainer = DarkErrorContainer,
    onErrorContainer = DarkOnErrorContainer,
    outline = DarkOutline,
    outlineVariant = DarkOutlineVariant,
)
