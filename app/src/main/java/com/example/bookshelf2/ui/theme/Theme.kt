/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.bookshelf2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val LightColorPalette = BookShelfColors(
    gradient6_1 = listOf(Shadow4, Ocean3, Shadow2, Ocean3, Shadow4),
    gradient6_2 = listOf(Rose4, Lavender3, Rose2, Lavender3, Rose4),
    gradient3_1 = listOf(Shadow2, Ocean3, Shadow4),
    gradient3_2 = listOf(Rose2, Lavender3, Rose4),
    gradient2_1 = listOf(Shadow4, Shadow11),
    gradient2_2 = listOf(Ocean3, Shadow3),
    gradient2_3 = listOf(Lavender3, Rose2),
    brand = Shadow5,
    brandSecondary = Ocean3,
    uiBackground = Neutral0,
    uiBorder = Neutral4,
    uiFloated = FunctionalGrey,
    textSecondary = Neutral7,
    textHelp = Neutral6,
    textInteractive = Neutral0,
    textLink = Ocean11,
    tornado1 = listOf(Shadow4, Ocean3),
    barColorPrimary = Neutral9,
    selectedIconBorderFill = FunctionalRed2,
    iconSecondary = Neutral7,
    iconInteractive = Neutral0,
    iconInteractiveInactive = Neutral8,
    error = FunctionalRed,
    notificationBadge = Color.White,
    border = Neutral6,
    cardColor = Neutral9,
    isDark = false,
    toggleButtonFilled = FunctionalRed3,
    bottomBarColor = Neutral9,
    textPlain = Neutral7,
    highlightedBorder = Neutral7,
    selectedBorderFill = Neutral1,
    toggleBorderOff = Neutral7,
    toggleBorderOn = FunctionalRed5,
    toggleFillOff = Neutral0,
    toggleFillOn = FunctionalRed4
)

private val DarkColorPalette = BookShelfColors(
    gradient6_1 = listOf(Shadow5, Ocean7, Shadow9, Ocean7, Shadow5),
    gradient6_2 = listOf(Rose11, Lavender7, Rose8, Lavender7, Rose11),
    gradient3_1 = listOf(Shadow9, Ocean7, Shadow5),
    gradient3_2 = listOf(Rose8, Lavender7, Rose11),
    gradient2_1 = listOf(Ocean3, Shadow3),
    gradient2_2 = listOf(Ocean4, Shadow2),
    gradient2_3 = listOf(Lavender3, Rose3),
    brand = Shadow1,
    brandSecondary = Ocean2,
    uiBackground = Neutral7,
    uiBorder = Neutral3,
    uiFloated = FunctionalDarkGrey,
    textPrimary = Shadow1,
    textSecondary = Neutral0,
    textHelp = Neutral1,
    textInteractive = Neutral1,
    textLink = Ocean2,
    tornado1 = listOf(Shadow4, Ocean3),
    barColorPrimary = Shadow1,
    selectedIconBorderFill = FunctionalRed2,
    iconSecondary = Neutral0,
    iconInteractive = Neutral0,
    iconInteractiveInactive = Neutral1,
    error = FunctionalRedDark,
    notificationBadge = Color.White,
    border = Neutral2,
    cardColor = Neutral8,
    isDark = true,
    toggleButtonFilled = FunctionalRed3,
    bottomBarColor = Color.Black,
    textPlain = Neutral1,
    highlightedBorder = Neutral1,
    selectedBorderFill = Neutral1,
    toggleBorderOff = Neutral0,
    toggleBorderOn = FunctionalRed5,
    toggleFillOff = Neutral7,
    toggleFillOn = FunctionalRed4
)

@Composable
fun BookShelfTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    ProvideBookShelfColors(colors) {
        MaterialTheme(
            colorScheme = debugColors(darkTheme),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object BookShelfTheme {
    val colors: BookShelfColors
        @Composable
        get() = LocalBookShelfColors.current
}

/**
 * BookShelf custom Color Palette
 */
@Immutable
data class BookShelfColors(
    val gradient6_1: List<Color>,
    val gradient6_2: List<Color>,
    val gradient3_1: List<Color>,
    val gradient3_2: List<Color>,
    val gradient2_1: List<Color>,
    val gradient2_2: List<Color>,
    val gradient2_3: List<Color>,
    val brand: Color,
    val brandSecondary: Color,
    val uiBackground: Color,
    val uiBorder: Color,
    val uiFloated: Color,
    val interactivePrimary: List<Color> = gradient2_1,
    val interactiveSecondary: List<Color> = gradient2_2,
    val interactiveMask: List<Color> = gradient6_1,
    val textPrimary: Color = brand,
    val textSecondary: Color,
    val textHelp: Color,
    val textInteractive: Color,
    val textLink: Color,
    val tornado1: List<Color>,
    val barColorPrimary: Color = brand,
    val selectedIconBorderFill: Color = brand,
    val iconSecondary: Color,
    val iconInteractive: Color,
    val iconInteractiveInactive: Color,
    val error: Color,
    val notificationBadge: Color,
    val border: Color,
    val cardColor: Color,
    val isDark: Boolean,
    val toggleButtonFilled: Color,
    val bottomBarColor: Color,
    val textPlain: Color,
    val highlightedBorder: Color,
    val selectedBorderFill: Color,
    val toggleBorderOff: Color,
    val toggleBorderOn: Color,
    val toggleFillOff: Color,
    val toggleFillOn: Color
)

@Composable
fun ProvideBookShelfColors(
    colors: BookShelfColors,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalBookShelfColors provides colors, content = content)
}

private val LocalBookShelfColors = staticCompositionLocalOf<BookShelfColors> {
    error("No BookShelfColorPalette provided")
}

/**
 * A Material [Colors] implementation which sets all colors to [debugColor] to discourage usage of
 * [MaterialTheme.colorScheme] in preference to [BookShelfTheme.colors].
 */
fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = Color.Magenta
) = ColorScheme(
    primary = debugColor,
    onPrimary = debugColor,
    primaryContainer = debugColor,
    onPrimaryContainer = debugColor,
    inversePrimary = debugColor,
    secondary = debugColor,
    onSecondary = debugColor,
    secondaryContainer = debugColor,
    onSecondaryContainer = debugColor,
    tertiary = debugColor,
    onTertiary = debugColor,
    tertiaryContainer = debugColor,
    onTertiaryContainer = debugColor,
    background = debugColor,
    onBackground = debugColor,
    surface = debugColor,
    onSurface = debugColor,
    surfaceVariant = debugColor,
    onSurfaceVariant = debugColor,
    surfaceTint = debugColor,
    inverseSurface = debugColor,
    inverseOnSurface = debugColor,
    error = debugColor,
    onError = debugColor,
    errorContainer = debugColor,
    onErrorContainer = debugColor,
    outline = debugColor,
    outlineVariant = debugColor,
    scrim = debugColor,
    surfaceBright = debugColor,
    surfaceDim = debugColor,
    surfaceContainer = debugColor,
    surfaceContainerHigh = debugColor,
    surfaceContainerHighest = debugColor,
    surfaceContainerLow = debugColor,
    surfaceContainerLowest = debugColor,
)
