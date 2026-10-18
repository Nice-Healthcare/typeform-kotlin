package com.typeform.ui.structure

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.models.Responses
import com.typeform.resources.Res
import com.typeform.resources.warning_24dp
import com.typeform.ui.models.Conclusion
import com.typeform.ui.preview.MaterialThemePreview
import org.jetbrains.compose.resources.vectorResource

/**
 * View the represents a general failure of the Typeform parser or responses.
 */
@Composable
internal fun RejectedView(
    modifier: Modifier = Modifier,
    responses: Responses,
    onClick: (Conclusion) -> Unit,
) {
    ScrollingContentView(
        title = "Finish",
        onClick = {
            onClick(Conclusion.Rejected(responses))
        },
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center,
        ) {
            CompositionLocalProvider(LocalContentColor provides (Color.Red)) {
                Icon(
                    imageVector = vectorResource(Res.drawable.warning_24dp),
                    contentDescription = null,
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
            responses = mapOf(),
            onClick = {},
        )
    }
}
