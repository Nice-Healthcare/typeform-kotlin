package com.typeform.ui.structure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.models.Position
import com.typeform.models.Responses
import com.typeform.models.TypeformException
import com.typeform.schema.structure.EndingScreen
import com.typeform.schema.structure.Form
import com.typeform.schema.structure.Screen
import com.typeform.schema.structure.WelcomeScreen
import com.typeform.ui.LocalSettings
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.LocalLocalization
import com.typeform.ui.models.LocalPresentation
import com.typeform.ui.models.NavigationAction
import com.typeform.ui.preview.MaterialThemePreview
import com.typeform.ui.preview.preview

/**
 * The [ScreenView] displays a 'welcome' or 'thank you' screen.
 */
@Composable
internal fun ScreenView(
    scaffoldPadding: PaddingValues,
    form: Form,
    screen: Screen,
    responses: Responses,
    actionHandler: (NavigationAction) -> Unit,
) {
    val settings = LocalSettings.current
    val isWelcomeScreen = screen is WelcomeScreen

    val next = try {
        form.nextPosition(Position.ScreenPosition(screen), responses)
    } catch (_: TypeformException) {
        null
    }

    ScrollingContentView(
        scaffoldPadding = scaffoldPadding,
        title = screen.properties.button_text ?: LocalLocalization.current.next,
        onClick = {
            if (next != null) {
                actionHandler(NavigationAction.PositionAction(next, responses))
            } else if (!isWelcomeScreen) {
                actionHandler(NavigationAction.ConclusionAction(Conclusion.Completed(responses, screen as EndingScreen)))
            } else {
                actionHandler(NavigationAction.ConclusionAction(Conclusion.Rejected(responses)))
            }
        },
    ) {
        Column(
            modifier = Modifier.padding(LocalPresentation.current.contentPadding),
            verticalArrangement = Arrangement.spacedBy(LocalPresentation.current.titleDescriptionVerticalSpacing),
        ) {
            screen.attachment?.let { attachment ->
                AttachmentView(
                    attachment = attachment,
                )
            }

            StyledTextView(
                text = screen.title,
                textStyle = MaterialTheme.typography.titleLarge,
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ScreenViewPreview() {
    val form = Form.preview
    val screen = form.welcomeScreens!!.first()

    MaterialThemePreview {
        ScreenView(
            scaffoldPadding = PaddingValues(0.dp),
            form = form,
            screen = screen,
            responses = mapOf(),
            actionHandler = { },
        )
    }
}
