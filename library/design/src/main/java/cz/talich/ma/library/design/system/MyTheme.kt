package cz.talich.ma.library.design.system

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val LocalColors = staticCompositionLocalOf { lightColorScheme() }
private val LocalTypography = staticCompositionLocalOf { myTypography() }

object MyTheme {
    val colorScheme: ColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

    @Composable
    operator fun invoke(
        darkTheme: Boolean = isSystemInDarkTheme(),
        content: @Composable () -> Unit,
    ) {
        val colors = DarkColorScheme.takeIf { darkTheme } ?: LightColorScheme
        val typography = myTypography()

        CompositionLocalProvider(
            LocalColors provides colors,
            LocalTypography provides typography,
        ) {
            MaterialTheme(
                colorScheme = colors,
                typography = typography,
                content = content
            )
        }
    }

    fun Color.alpha(alpha: Int): Color {
        require(alpha in 0..100) { "Alpha value has to be in range 0..100" }
        return copy(alpha = alpha / 100f)
    }
}
