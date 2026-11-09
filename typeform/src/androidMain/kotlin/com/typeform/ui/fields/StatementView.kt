package com.typeform.ui.fields

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.schema.structure.Statement
import com.typeform.ui.components.ContentContainerView
import com.typeform.ui.preview.FieldPreview

@Composable
internal fun StatementView(
    properties: Statement,
    modifier: Modifier = Modifier,
) {
    ContentContainerView(
        modifier = modifier,
        description = properties.description,
    ) {}
}

@Composable
@PreviewLightDark
private fun StatementViewPreview() {
    FieldPreview {
        StatementView(
            properties = Statement(
                hide_marks = false,
                button_text = "Anything",
                description = "I'm making a statement!",
            ),
        )
    }
}
