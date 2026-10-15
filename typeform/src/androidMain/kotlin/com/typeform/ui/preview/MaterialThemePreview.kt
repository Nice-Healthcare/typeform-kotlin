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
import com.typeform.ui.LocalLocalization
import com.typeform.ui.LocalLogic
import com.typeform.ui.LocalPresentation
import com.typeform.ui.LocalTextStyles
import com.typeform.ui.components.HeadlineContainerView
import com.typeform.ui.components.TopNavigationBar
import com.typeform.ui.models.Appearance
import com.typeform.ui.structure.ScrollingContentView

@Composable
internal fun MaterialThemePreview(
    darkTheme: Boolean = isSystemInDarkTheme(),
    appearance: Appearance = Appearance(),
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
            LocalLocalization provides appearance.localization,
            LocalPresentation provides appearance.presentation,
            LocalTextStyles provides appearance.textStyles,
            LocalLogic provides appearance.additionalLogic,
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
    appearance: Appearance = Appearance(),
    content: @Composable () -> Unit,
) {
    MaterialThemePreview(
        appearance = appearance,
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
