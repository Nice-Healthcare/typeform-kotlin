package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.schema.Statement
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.Settings

@Composable
internal fun StatementView(
    settings: Settings,
    properties: Statement,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(settings.presentation.descriptionContentVerticalSpacing),
    ) {
        properties.description?.let {
            StyledTextView(
                text = it,
                textStyle = MaterialTheme.typography.caption,
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun StatementViewPreview() {
    StatementView(
        settings = Settings(),
        properties = Statement(
            hide_marks = false,
            button_text = "Continue",
            description = null,
        ),
    )
}
