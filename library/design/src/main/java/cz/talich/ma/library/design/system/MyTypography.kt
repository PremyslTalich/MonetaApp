package cz.talich.ma.library.design.system

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color

private val initialTypography = Typography()

fun myTypography(
    textPrimary: Color = Color.Unspecified
) = with(initialTypography) {
    copy(
        bodyLarge = bodyLarge.copy(color = textPrimary),
        bodyMedium = bodyMedium.copy(color = textPrimary),
        bodySmall = bodySmall.copy(color = textPrimary),
        displayLarge = displayLarge.copy(color = textPrimary),
        displayMedium = displayMedium.copy(color = textPrimary),
        displaySmall = displaySmall.copy(color = textPrimary),
        headlineLarge = headlineLarge.copy(color = textPrimary),
        headlineMedium = headlineMedium.copy(color = textPrimary),
        headlineSmall = headlineSmall.copy(color = textPrimary),
        labelLarge = labelLarge.copy(color = textPrimary),
        labelMedium = labelMedium.copy(color = textPrimary),
        labelSmall = labelSmall.copy(color = textPrimary),
        titleLarge = titleLarge.copy(color = textPrimary),
        titleMedium = titleMedium.copy(color = textPrimary),
        titleSmall = titleSmall.copy(color = textPrimary),
    )
}
