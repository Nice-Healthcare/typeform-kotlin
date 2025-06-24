package com.typeform.ui.structure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import com.typeform.models.Position
import com.typeform.models.Responses
import com.typeform.models.TypeformException
import com.typeform.schema.Form
import com.typeform.schema.Screen
import com.typeform.schema.ThankYouScreen
import com.typeform.schema.WelcomeScreen
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.NavigationAction
import com.typeform.ui.models.Settings
import com.typeform.ui.preview.ThemePreview
import com.typeform.ui.preview.preview

/**
 * The [ScreenView] displays a 'welcome' or 'thank you' screen.
 */
@Composable
internal fun ScreenView(
    scaffoldPadding: PaddingValues,
    form: Form,
    settings: Settings,
    screen: Screen,
    responses: Responses,
    imageLoader: ImageLoader? = null,
    actionHandler: (NavigationAction) -> Unit,
) {
    val isWelcomeScreen = screen is WelcomeScreen

    val next = try {
        form.nextPosition(Position.ScreenPosition(screen), responses)
    } catch (_: TypeformException) {
        null
    }

    ScrollingContentView(
        scaffoldPadding = scaffoldPadding,
        settings = settings,
        title = screen.properties.button_text ?: settings.localization.next,
        onClick = {
            if (next != null) {
                actionHandler(NavigationAction.PositionAction(next, responses))
            } else if (!isWelcomeScreen) {
                actionHandler(NavigationAction.ConclusionAction(Conclusion.Completed(responses, screen as ThankYouScreen)))
            } else {
                actionHandler(NavigationAction.ConclusionAction(Conclusion.Rejected(responses)))
            }
        },
    ) {
        Column(
            modifier = Modifier.padding(settings.presentation.contentPadding),
            verticalArrangement = Arrangement.spacedBy(settings.presentation.titleDescriptionVerticalSpacing),
        ) {
            screen.attachment?.let { attachment ->
                AttachmentView(
                    attachment = attachment,
                    imageLoader = imageLoader,
                )
            }

            StyledTextView(
                text = screen.title,
                textStyle = MaterialTheme.typography.h5,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ScreenViewPreview() {
    val form = Form.preview
    val screen = form.welcome_screens!!.first()

    ThemePreview {
        ScreenView(
            scaffoldPadding = PaddingValues(0.dp),
            form = form,
            settings = Settings(),
            screen = screen,
            responses = mapOf(),
            actionHandler = { },
        )
    }
}
