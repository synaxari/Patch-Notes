package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.data.local.ThemeSetting

private val ElegantDarkColorScheme = darkColorScheme(
    primary = ElegantPrimary,
    onPrimary = ElegantOnPrimary,
    primaryContainer = ElegantSurfaceVariant,
    onPrimaryContainer = ElegantPrimary,
    secondary = ElegantSecondary,
    onSecondary = ElegantOnSecondary,
    secondaryContainer = ElegantSurface,
    onSecondaryContainer = ElegantSecondary,
    tertiary = ElegantTertiary,
    onTertiary = ElegantOnSecondary,
    background = ElegantDarkBg,
    onBackground = TextPrimary,
    surface = ElegantSurface,
    onSurface = TextPrimary,
    surfaceVariant = ElegantSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = ElegantBorder,
    outlineVariant = ElegantBorderLight,
    error = NerfRed,
    onError = NerfRedDark
)

private val ElegantLightColorScheme = lightColorScheme(
    primary = LightPrimary,
    onPrimary = LightOnPrimary,
    primaryContainer = LightSurfaceVariant,
    onPrimaryContainer = LightPrimary,
    secondary = LightSecondary,
    onSecondary = LightOnSecondary,
    secondaryContainer = LightSurface,
    onSecondaryContainer = LightSecondary,
    tertiary = LightTertiary,
    onTertiary = LightOnSecondary,
    background = LightBackground,
    onBackground = LightTextPrimary,
    surface = LightSurface,
    onSurface = LightTextPrimary,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = LightTextSecondary,
    outline = LightBorder,
    outlineVariant = LightBorderLight,
    error = NerfRedDark,
    onError = LightOnPrimary
)

@Composable
fun MyApplicationTheme(
    themeSetting: ThemeSetting = ThemeSetting.SYSTEM,
    content: @Composable () -> Unit
) {
    val isDark = when (themeSetting) {
        ThemeSetting.DARK -> true
        ThemeSetting.LIGHT -> false
        ThemeSetting.SYSTEM -> isSystemInDarkTheme()
    }

    val colorScheme = if (isDark) ElegantDarkColorScheme else ElegantLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
