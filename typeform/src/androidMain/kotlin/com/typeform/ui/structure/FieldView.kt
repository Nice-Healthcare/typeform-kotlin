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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.typeform.models.Position
import com.typeform.models.ResponseValue
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
import com.typeform.ui.fields.RatingView
import com.typeform.ui.fields.ShortTextView
import com.typeform.ui.fields.StatementView
import com.typeform.ui.fields.YesNoView
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.NavigationAction
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
    val validated: MutableState<Boolean> = remember { mutableStateOf(false) }
    val next: MutableState<Position?> = remember { mutableStateOf(null) }

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
        next.value = try {
            form.nextPosition(Position.FieldPosition(field, group), responses)
        } catch (_: TypeformException) {
            null
        }
    }

    fun handleResponse(ref: String, value: ResponseValue?) {
        if (value != null) {
            responses[ref] = value
        } else {
            responses.remove(ref)
        }
        determineNext()
    }

    fun handleValidation(isValid: Boolean) {
        validated.value = isValid
        determineNext()
    }

    ScrollingContentView(
        scaffoldPadding = scaffoldPadding,
        settings = settings,
        title = nextTitle,
        enabled = validated.value,
        header = header,
        onClick = {
            next.value?.let {
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
                        ref = field.ref,
                        properties = field.properties.properties,
                        responses = responses,
                        responseHandler = { ref, response ->
                            handleResponse(ref, response)
                        },
                        validations = field.validations,
                        validationHandler = { validated ->
                            handleValidation(validated)
                        },
                    )
                }
                is FieldProperties.DropdownProperties -> {
                    DropdownView(
                        settings = settings,
                        ref = field.ref,
                        properties = field.properties.properties,
                        responses = responses,
                        responseHandler = { ref, response ->
                            handleResponse(ref, response)
                        },
                        validations = field.validations,
                        validationHandler = { validated ->
                            handleValidation(validated)
                        },
                    )
                }
                is FieldProperties.GroupProperties -> {
                    // No additional content
                    handleValidation(true)
                }
                is FieldProperties.LongTextProperties -> {
                    LongTextView(
                        settings = settings,
                        ref = field.ref,
                        properties = field.properties.properties,
                        responses = responses,
                        responseHandler = { ref, response ->
                            handleResponse(ref, response)
                        },
                        validations = field.validations,
                        validationHandler = { validated ->
                            handleValidation(validated)
                        },
                    )
                }
                is FieldProperties.MultipleChoiceProperties -> {
                    MultipleChoiceView(
                        settings = settings,
                        ref = field.ref,
                        properties = field.properties.properties,
                        responses = responses,
                        responseHandler = { ref, response ->
                            handleResponse(ref, response)
                        },
                        validations = field.validations,
                        validationHandler = { validated ->
                            handleValidation(validated)
                        },
                    )
                }
                is FieldProperties.NumberProperties -> {
                    NumberView(
                        settings = settings,
                        ref = field.ref,
                        properties = field.properties.properties,
                        responses = responses,
                        responseHandler = { ref, response ->
                            handleResponse(ref, response)
                        },
                        validations = field.validations,
                        validationHandler = { validated ->
                            handleValidation(validated)
                        },
                    )
                }
                is FieldProperties.RatingProperties -> {
                    RatingView(
                        settings = settings,
                        ref = field.ref,
                        properties = field.properties.properties,
                        responses = responses,
                        responseHandler = { ref, response ->
                            handleResponse(ref, response)
                        },
                        validations = field.validations,
                        validationHandler = { validated ->
                            handleValidation(validated)
                        },
                    )
                }
                is FieldProperties.ShortTextProperties -> {
                    ShortTextView(
                        settings = settings,
                        ref = field.ref,
                        properties = field.properties.properties,
                        responses = responses,
                        responseHandler = { ref, response ->
                            handleResponse(ref, response)
                        },
                        validations = field.validations,
                        validationHandler = { validated ->
                            handleValidation(validated)
                        },
                    )
                }
                is FieldProperties.StatementProperties -> {
                    StatementView(
                        settings = settings,
                        properties = field.properties.properties,
                        validations = field.validations,
                        validationHandler = { validated ->
                            handleValidation(validated)
                        },
                    )
                }
                is FieldProperties.YesNoProperties -> {
                    YesNoView(
                        settings = settings,
                        ref = field.ref,
                        properties = field.properties.properties,
                        responses = responses,
                        responseHandler = { ref, response ->
                            handleResponse(ref, response)
                        },
                        validations = field.validations,
                        validationHandler = { validated ->
                            handleValidation(validated)
                        },
                    )
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
//            "", //References.group,
//            "", //References.longText,
//            "", //References.multipleChoice_Many,
//            "", //References.multipleChoice_One,
//            "", //References.number,
//            "", //References.rating,
//            "", //References.shortText,
//            "", //References.statement,
//            "", //References.yesNo,
        )
}
