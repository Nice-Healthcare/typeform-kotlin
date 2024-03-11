package com.typeform.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.typeform.ui.models.Settings

@Composable
internal fun IntermittentChoiceButton(
    settings: Settings,
    text: String,
    modifier: Modifier = Modifier,
    allowMultiple: Boolean? = false,
    selected: Boolean = false,
    onClick: () -> Unit,
) {
    val backgroundColor = if (allowMultiple == null) {
        if (selected) settings.rating.selectedBackgroundColor else settings.rating.unselectedBackgroundColor
    } else {
        if (selected) settings.interaction.selectedBackgroundColor else settings.interaction.unselectedBackgroundColor
    }

    val strokeColor = if (allowMultiple == null) {
        if (selected) settings.rating.selectedStrokeColor else settings.rating.unselectedStrokeColor
    } else {
        if (selected) settings.interaction.selectedStrokeColor else settings.interaction.unselectedStrokeColor
    }

    val strokeWidth = if (allowMultiple == null) {
        if (selected) settings.rating.selectedStrokeWidth else settings.rating.unselectedStrokeWidth
    } else {
        if (selected) settings.interaction.selectedStrokeWidth else settings.interaction.unselectedStrokeWidth
    }

    val foregroundColor = if (allowMultiple == null) {
        if (selected) settings.rating.selectedForegroundColor else settings.rating.unselectedForegroundColor
    } else {
        MaterialTheme.colors.onBackground
    }
    
    val textModifier = if (allowMultiple == null) Modifier else Modifier.fillMaxWidth()

    Button(
        onClick = onClick,
        modifier = modifier,
        elevation = null,
        border = BorderStroke(
            width = strokeWidth,
            color = strokeColor,
        ),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
        ),
        contentPadding = PaddingValues(),
    ) {
        Row(
            modifier = Modifier.padding(settings.interaction.padding),
            horizontalArrangement = Arrangement.spacedBy(settings.interaction.horizontalSpacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            allowMultiple?.let {
                ChoiceIndicatorView(
                    settings = settings,
                    allowMultiple = it,
                    selected = selected,
                )
            }

            StyledTextView(
                text = text,
                modifier = textModifier,
                textStyle = MaterialTheme.typography.body1,
                color = foregroundColor,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun IntermittentChoiceButtonPreview() {
    IntermittentChoiceButton(
        settings = Settings(),
        text = "Example Button",
    ) {
    }
}
