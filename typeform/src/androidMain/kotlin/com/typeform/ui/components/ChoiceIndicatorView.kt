package com.typeform.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.typeform.ui.models.Settings

@Composable
internal fun ChoiceIndicatorView(
    settings: Settings,
    allowMultiple: Boolean,
    selected: Boolean,
) {
    Box(
        modifier = Modifier
            .height(20.dp)
            .width(20.dp)
            .background(Color.Transparent),
    ) {
        if (allowMultiple) {
            val background = if (selected) settings.checkbox.selectedBackgroundColor else settings.checkbox.unselectedBackgroundColor
            val stroke = if (selected) settings.checkbox.selectedStrokeColor else settings.checkbox.unselectedStrokeColor

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(settings.checkbox.cornerRadius))
                    .background(background),
            ) {}

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, stroke, RoundedCornerShape(settings.checkbox.cornerRadius)),
            ) {}

            if (selected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = settings.checkbox.selectedForegroundColor,
                )
            }
        } else {
            val background = if (selected) settings.radio.selectedBackgroundColor else settings.radio.unselectedBackgroundColor
            val stroke = if (selected) settings.radio.selectedStrokeColor else settings.radio.unselectedStrokeColor

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(background),
            ) { }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, stroke, CircleShape),
            ) { }

            if (selected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(settings.radio.selectedForegroundColor),
                ) { }
            }
        }
    }
}

@Preview
@Composable
private fun ChoiceIndicatorViewPreview(
    @PreviewParameter(ChoiceIndicatorParameterProvider::class) parameters: Pair<Boolean, Boolean>,
) {
    ChoiceIndicatorView(
        settings = Settings(),
        allowMultiple = parameters.first,
        selected = parameters.second,
    )
}

private class ChoiceIndicatorParameterProvider : PreviewParameterProvider<Pair<Boolean, Boolean>> {
    override val values: Sequence<Pair<Boolean, Boolean>>
        get() = sequenceOf(
            Pair(false, false),
            Pair(false, true),
            Pair(true, false),
            Pair(true, true),
        )
}
