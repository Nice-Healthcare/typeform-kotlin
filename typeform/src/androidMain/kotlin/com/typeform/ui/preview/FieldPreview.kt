package com.typeform.ui.preview

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.typeform.schema.structure.Attachment
import com.typeform.ui.LocalColors
import com.typeform.ui.components.HeadlineContainerView
import com.typeform.ui.components.TopNavigationBar
import com.typeform.ui.structure.ScrollingContentView

/**
 * Preview which provides the structural elements needed for displaying 'fields'.
 */
@Composable
internal fun FieldPreview(
    headline: String = "Headline",
    attachment: Attachment? = null,
    content: @Composable () -> Unit,
) {
    val colors = LocalColors.current

    MaterialThemePreview {
        Scaffold(
            topBar = {
                TopNavigationBar(
                    showBackNavigation = true,
                    onBack = { },
                    onExit = { },
                )
            },
            containerColor = colors.surface,
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
