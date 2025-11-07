package com.typeform.ui.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class DarkThemePreviewParameter : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() = sequenceOf(false, true)
}

@Composable
fun MaterialThemePreview(
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = if (darkTheme) {
            darkColorScheme()
        } else {
            lightColorScheme()
        },
    ) {
        Surface {
            content()
        }
    }
}
