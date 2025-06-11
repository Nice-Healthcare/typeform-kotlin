package com.typeform.ui.structure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.typeform.models.Responses
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.Settings

/**
 * View the represents a general failure of the Typeform parser or responses.
 */
@Composable
internal fun RejectedView(
    scaffoldPadding: PaddingValues,
    settings: Settings,
    responses: Responses,
    onClick: (Conclusion) -> Unit,
) {
    ScrollingContentView(
        scaffoldPadding = scaffoldPadding,
        settings = settings,
        title = "Finish",
        onClick = {
            onClick(Conclusion.Rejected(responses))
        },
    ) {
        Column(
            modifier = Modifier.padding(settings.presentation.contentPadding),
            verticalArrangement = Arrangement.spacedBy(settings.presentation.titleDescriptionVerticalSpacing),
        ) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colors.error,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RejectedViewPreview() {
    RejectedView(
        scaffoldPadding = PaddingValues(0.dp),
        settings = Settings(),
        responses = mapOf(),
        onClick = {},
    )
}
