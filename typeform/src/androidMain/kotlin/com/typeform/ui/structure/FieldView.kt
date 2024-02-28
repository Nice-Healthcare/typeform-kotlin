package com.typeform.ui.structure

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.typeform.models.Position
import com.typeform.models.Responses
import com.typeform.models.TypeformException
import com.typeform.schema.Field
import com.typeform.schema.FieldProperties
import com.typeform.schema.FieldType
import com.typeform.schema.Form
import com.typeform.schema.Group
import com.typeform.schema.ThankYouScreen
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.fields.DateView
import com.typeform.ui.fields.DropdownView
import com.typeform.ui.fields.LongTextView
import com.typeform.ui.fields.MultipleChoiceView
import com.typeform.ui.fields.NumberView
import com.typeform.ui.fields.OpinionScaleView
import com.typeform.ui.fields.RatingView
import com.typeform.ui.fields.ShortTextView
import com.typeform.ui.fields.StatementView
import com.typeform.ui.fields.YesNoView
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.NavigationAction
import com.typeform.ui.models.ResponseState
import com.typeform.ui.models.Settings
import com.typeform.ui.preview.preview
import com.typeform.ui.preview.previewDate
import com.typeform.ui.preview.previewDropdown

/**
 * Structure used to display individual [Field] types.
 */
@Composable
internal fun FieldView(
    scaffoldPadding: PaddingValues,
    form: Form,
    settings: Settings,
    field: Field,
    group: Group?,
    responses: Responses,
    header: (@Composable () -> Unit)? = null,
    actionHandler: (NavigationAction) -> Unit,
) {
    var collectedResponses: Responses by remember { mutableStateOf(responses) }
    var responseState: ResponseState by remember {
        mutableStateOf(
            ResponseState(
                field = field,
                responses = responses,
            )
        )
    }
    var next: Position? by remember { mutableStateOf(null) }

    val nextTitle = when (field.properties) {
        is FieldProperties.GroupProperties -> {
            field.properties.properties.button_text
        }
        is FieldProperties.StatementProperties -> {
            field.properties.properties.button_text
        }
        else -> {
            settings.localization.next
        }
    }

    val titleStyle = when (field.type) {
        FieldType.GROUP -> {
            MaterialTheme.typography.h4
        }
        else -> {
            MaterialTheme.typography.h5
        }
    }

    fun determineNext() {
        next = try {
            form.nextPosition(Position.FieldPosition(field, group), collectedResponses)
        } catch (_: TypeformException) {
            null
        }
    }

    fun handleResponseState(state: ResponseState) {
        responseState = state

        val currentResponses = collectedResponses

        if (state.response != null) {
            currentResponses[field.ref] = state.response
        } else {
            currentResponses.remove(field.ref)
        }

        collectedResponses = currentResponses

        determineNext()
    }

    ScrollingContentView(
        scaffoldPadding = scaffoldPadding,
        settings = settings,
        title = nextTitle,
        enabled = (next != null && !responseState.invalid),
        header = header,
        onClick = {
            next?.let {
                when (it) {
                    is Position.ScreenPosition -> {
                        if (it.screen is ThankYouScreen && settings.presentation.skipEndingScreen) {
                            actionHandler(NavigationAction.ConclusionAction(Conclusion.Completed(responses, it.screen)))
                        } else {
                            actionHandler(NavigationAction.PositionAction(it))
                        }
                    }
                    else -> {
                        actionHandler(NavigationAction.PositionAction(it))
                    }
                }
            }
        },
    ) {
        Column(
            modifier = Modifier.padding(settings.presentation.contentPadding),
            verticalArrangement = Arrangement.spacedBy(settings.presentation.titleDescriptionVerticalSpacing),
        ) {
            StyledTextView(
                text = field.title,
                textStyle = titleStyle,
            )

            when (field.properties) {
                is FieldProperties.DateStampProperties -> {
                    DateView(
                        settings = settings,
                        properties = field.properties.properties,
                        responseState = responseState,
                        validations = field.validations,
                    ) {
                        handleResponseState(it)
                    }
                }
                is FieldProperties.DropdownProperties -> {
                    DropdownView(
                        settings = settings,
                        properties = field.properties.properties,
                        responseState = responseState,
                        validations = field.validations,
                    ) {
                        handleResponseState(it)
                    }
                }
                is FieldProperties.GroupProperties -> {
                    // No additional content
                }
                is FieldProperties.LongTextProperties -> {
                    LongTextView(
                        settings = settings,
                        properties = field.properties.properties,
                        responseState = responseState,
                        validations = field.validations,
                    ) {
                        handleResponseState(it)
                    }
                }
                is FieldProperties.MultipleChoiceProperties -> {
                    MultipleChoiceView(
                        settings = settings,
                        properties = field.properties.properties,
                        responseState = responseState,
                        validations = field.validations,
                    ) {
                        handleResponseState(it)
                    }
                }
                is FieldProperties.NumberProperties -> {
                    NumberView(
                        settings = settings,
                        properties = field.properties.properties,
                        responseState = responseState,
                        validations = field.validations,
                    ) {
                        handleResponseState(it)
                    }
                }
                is FieldProperties.OpinionScaleProperties -> {
                    OpinionScaleView(
                        settings = settings,
                        properties = field.properties.properties,
                        responseState = responseState,
                        validations = field.validations,
                    ) {
                        handleResponseState(it)
                    }
                }
                is FieldProperties.RatingProperties -> {
                    RatingView(
                        settings = settings,
                        properties = field.properties.properties,
                        responseState = responseState,
                        validations = field.validations,
                    ) {
                        handleResponseState(it)
                    }
                }
                is FieldProperties.ShortTextProperties -> {
                    ShortTextView(
                        settings = settings,
                        properties = field.properties.properties,
                        responseState = responseState,
                        validations = field.validations,
                    ) {
                        handleResponseState(it)
                    }
                }
                is FieldProperties.StatementProperties -> {
                    StatementView(
                        settings = settings,
                        properties = field.properties.properties,
                    )
                }
                is FieldProperties.YesNoProperties -> {
                    YesNoView(
                        settings = settings,
                        properties = field.properties.properties,
                        responseState = responseState,
                        validations = field.validations,
                    ) {
                        handleResponseState(it)
                    }
                }
            }
        }
    }

    determineNext()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FieldViewPreview(
    @PreviewParameter(FieldParameterProvider::class) reference: String,
) {
    val form = Form.preview
    val field = form.fieldWithRef(reference) ?: throw Resources.NotFoundException()

    FieldView(
        scaffoldPadding = PaddingValues(0.dp),
        form = form,
        settings = Settings(),
        field = field,
        group = null,
        responses = mutableMapOf(),
        header = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Magenta),
            ) {
                Text("Intake for: Patient Name")
            }
        },
        actionHandler = { },
    )
}

private class FieldParameterProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String>
        get() = sequenceOf(
            Field.previewDate.ref,
            Field.previewDropdown.ref,
        )
}
