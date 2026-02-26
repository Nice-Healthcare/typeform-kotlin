package com.typeform.ui.preview

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.typeform.schema.structure.Attachment
import com.typeform.ui.LocalAppearance
import com.typeform.ui.LocalLocalization
import com.typeform.ui.LocalPresentation
import com.typeform.ui.components.HeadlineContainerView
import com.typeform.ui.components.TopNavigationBar
import com.typeform.ui.models.Settings
import com.typeform.ui.structure.ScrollingContentView

@Composable
internal fun MaterialThemePreview(
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

/**
 * Preview which provides all structural elements.
 */
@Composable
internal fun TypeformPreview(
    headline: String = "Headline",
    attachment: Attachment? = null,
    settings: Settings = Settings(),
    content: @Composable () -> Unit,
) {
    MaterialThemePreview(
        settings = settings,
    ) {
        Scaffold(
            topBar = {
                TopNavigationBar(
                    showBackNavigation = true,
                    onBack = { },
                ) { }
            },
        ) { scaffoldPadding ->
            ScrollingContentView(
                modifier = Modifier.padding(scaffoldPadding),
                title = "Big Button",
                onClick = {
                },
            ) {
                HeadlineContainerView(
                    headline = headline,
                    attachment = attachment,
                ) {
                    content()
                }
            }
        }
    }
}
