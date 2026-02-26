package com.typeform.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.typeform.ui.LocalPresentation
import com.typeform.ui.models.Appearance

/**
 * Container which displays the primary interactive content on any view.
 * This would typically be a 'Field' (Schema::Question) view.
 */
@Composable
internal fun ContentContainerView(
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    description: String? = null,
    content: @Composable () -> Unit,
) {
    val presentation = LocalPresentation.current

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(presentation.contentVerticalSpacing),
        horizontalAlignment = horizontalAlignment,
    ) {
        description?.let {
            TextView(
                text = it,
                typeStyle = Appearance.TypeStyle.TITLE,
            )
        }

        content()
    }
}
