package com.typeform.ui.preview

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.typeform.ui.LocalAppearance
import com.typeform.ui.LocalLocalization
import com.typeform.ui.LocalPresentation
import com.typeform.ui.models.Settings

@Composable
fun MaterialThemePreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    settings: Settings = Settings(),
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) {
            darkColorScheme()
        } else {
            lightColorScheme()
        },
    ) {
        CompositionLocalProvider(
            LocalLocalization provides settings.localization,
            LocalPresentation provides settings.presentation,
            LocalAppearance provides settings.appearance,
        ) {
            Surface {
                content()
            }
        }
    }
}
