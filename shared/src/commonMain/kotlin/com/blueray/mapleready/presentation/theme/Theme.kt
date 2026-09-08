package com.blueray.mapleready.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val MapleReadyColorScheme = lightColorScheme(
    primary = MapleRed,
    onPrimary = SurfaceLight,

    secondary = AutumnOrange,
    onSecondary = SurfaceLight,

    tertiary = MapleGold,
    onTertiary = DarkText,

    background = WarmBackground,
    onBackground = DarkText,

    surface = SurfaceLight,
    onSurface = DarkText
)

@Composable
fun MapleReadyTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MapleReadyColorScheme,
        content = content
    )
}