package com.typeform.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.typeform.schema.structure.Attachment
import com.typeform.ui.LocalPresentation
import com.typeform.ui.models.Script
import com.typeform.ui.structure.AttachmentView

/**
 * Container which displays the titles and attachments that are associated
 * to questions.
 */
@Composable
internal fun HeadlineContainerView(
    modifier: Modifier = Modifier,
    headline: String? = null,
    attachment: Attachment? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    val presentation = LocalPresentation.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(presentation.headlineContainerSpacing),
    ) {
        headline?.let {
            TextView(
                text = it,
                script = Script.HEADLINE,
            )
        }

        attachment?.let { attachment ->
            AttachmentView(
                attachment = attachment,
            )
        }

        content()
    }
}
