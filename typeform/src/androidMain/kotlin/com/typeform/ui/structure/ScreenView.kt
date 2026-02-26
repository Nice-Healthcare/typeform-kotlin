package com.typeform.ui.structure

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
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
import com.typeform.ui.LocalLocalization
import com.typeform.ui.components.HeadlineContainerView
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.NavigationAction
import com.typeform.ui.preview.MaterialThemePreview
import com.typeform.ui.preview.preview

/**
 * Container view that displays a [Screen] ('welcome' or 'thank you').
 */
@Composable
internal fun ScreenView(
    scaffoldPadding: PaddingValues,
    form: Form,
    screen: Screen,
    responses: Responses,
    actionHandler: (NavigationAction) -> Unit,
) {
    val isWelcomeScreen = screen is WelcomeScreen

    val next = try {
        form.nextPosition(Position.ScreenPosition(screen), responses)
    } catch (_: TypeformException) {
        null
    }

    ScrollingContentView(
        modifier = Modifier.padding(scaffoldPadding),
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
        HeadlineContainerView(
            headline = screen.title,
            attachment = screen.attachment,
        ) { }
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
