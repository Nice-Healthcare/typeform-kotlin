package com.typeform.ui.structure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.models.Responses
import com.typeform.resources.Res
import com.typeform.resources.warning_24dp
import com.typeform.ui.LocalPresentation
import com.typeform.ui.models.Conclusion
import com.typeform.ui.preview.MaterialThemePreview
import org.jetbrains.compose.resources.vectorResource

/**
 * View the represents a general failure of the Typeform parser or responses.
 */
@Composable
internal fun RejectedView(
    scaffoldPadding: PaddingValues,
    responses: Responses,
    onClick: (Conclusion) -> Unit,
) {
    val presentation = LocalPresentation.current

    ScrollingContentView(
        scaffoldPadding = scaffoldPadding,
        title = "Finish",
        onClick = {
            onClick(Conclusion.Rejected(responses))
        },
    ) {
        Column(
            modifier = Modifier.padding(presentation.contentPadding),
            verticalArrangement = Arrangement.spacedBy(presentation.titleDescriptionVerticalSpacing),
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.errorContainer,
            ) {
                Icon(
                    imageVector = vectorResource(Res.drawable.warning_24dp),
                    contentDescription = "Indicator of an invalid state.",
                )
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun RejectedViewPreview() {
    MaterialThemePreview {
        RejectedView(
            scaffoldPadding = PaddingValues(0.dp),
            responses = mapOf(),
            onClick = {},
        )
    }
}
