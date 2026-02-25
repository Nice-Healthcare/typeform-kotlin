package com.typeform.ui.structure

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.models.Position
import com.typeform.models.Responses
import com.typeform.models.TypeformException
import com.typeform.schema.structure.EndingScreen
import com.typeform.schema.structure.Field
import com.typeform.schema.structure.FieldProperties
import com.typeform.schema.structure.Form
import com.typeform.schema.structure.Group
import com.typeform.ui.LocalSettings
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.fields.DateView
import com.typeform.ui.fields.DropdownView
import com.typeform.ui.fields.FileUploadView
import com.typeform.ui.fields.LongTextView
import com.typeform.ui.fields.MatrixView
import com.typeform.ui.fields.MultipleChoiceView
import com.typeform.ui.fields.NumberView
import com.typeform.ui.fields.OpinionScaleView
import com.typeform.ui.fields.RatingView
import com.typeform.ui.fields.ShortTextView
import com.typeform.ui.fields.YesNoView
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.LocalLocalization
import com.typeform.ui.models.LocalPresentation
import com.typeform.ui.models.NavigationAction
import com.typeform.ui.models.ResponseState
import com.typeform.ui.preview.MaterialThemePreview
import com.typeform.ui.preview.preview
import com.typeform.ui.preview.previewStatement

/**
 * Structure used to display individual [Field] types.
 */
@Composable
internal fun FieldView(
    scaffoldPadding: PaddingValues,
    form: Form,
    field: Field,
    group: Group?,
    responses: Responses,
    header: (@Composable () -> Unit)? = null,
    actionHandler: (NavigationAction) -> Unit,
) {
    val settings = LocalSettings.current
    val presentation = LocalPresentation.current
    var collectedResponses: Responses by remember { mutableStateOf(responses) }
    var responseState: ResponseState by remember {
        mutableStateOf(
            ResponseState(
                field = field,
                responses = responses,
            ),
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
            LocalLocalization.current.next
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

        collectedResponses = if (state.response != null) {
            collectedResponses + (field.ref to state.response)
        } else {
            collectedResponses.filterNot { it.key == field.ref }
        }

        determineNext()
    }

    LaunchedEffect(Unit) {
        determineNext()
    }

    ScrollingContentView(
        scaffoldPadding = scaffoldPadding,
        title = nextTitle,
        enabled = (next != null && !responseState.invalid),
        header = header,
        onClick = {
            next?.let {
                when (it) {
                    is Position.ScreenPosition -> {
                        if (it.screen is EndingScreen && presentation.skipEndingScreen) {
                            actionHandler(NavigationAction.ConclusionAction(Conclusion.Completed(collectedResponses, it.screen)))
                        } else {
                            actionHandler(NavigationAction.PositionAction(it, collectedResponses))
                        }
                    }
                    else -> {
                        actionHandler(NavigationAction.PositionAction(it, collectedResponses))
                    }
                }
            }
        },
    ) {
        Column(
            modifier = Modifier.padding(presentation.contentPadding),
            verticalArrangement = Arrangement.spacedBy(presentation.titleDescriptionVerticalSpacing),
        ) {
            StyledTextView(
                text = field.title,
                textStyle = MaterialTheme.typography.titleMedium,
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(presentation.descriptionContentVerticalSpacing),
            ) {
                field.attachment?.let {
                    AttachmentView(
                        attachment = it,
                    )
                }

                when (field.properties) {
                    is FieldProperties.DateStampProperties -> {
                        DateView(
                            properties = field.properties.properties,
                            responseState = responseState,
                            validations = field.validations,
                        ) {
                            handleResponseState(it)
                        }
                    }
                    is FieldProperties.DropdownProperties -> {
                        DropdownView(
                            properties = field.properties.properties,
                            responseState = responseState,
                            validations = field.validations,
                        ) {
                            handleResponseState(it)
                        }
                    }
                    is FieldProperties.FileUploadProperties -> {
                        FileUploadView(
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
                            properties = field.properties.properties,
                            responseState = responseState,
                            validations = field.validations,
                        ) {
                            handleResponseState(it)
                        }
                    }
                    is FieldProperties.MatrixProperties -> {
                        // Note `properties.validations` as `Matrix` utilize their `fields` validations.
                        MatrixView(
                            properties = field.properties.properties,
                            responseState = responseState,
                            validations = field.properties.properties.validations,
                        ) {
                            handleResponseState(it)
                        }
                    }
                    is FieldProperties.MultipleChoiceProperties -> {
                        MultipleChoiceView(
                            properties = field.properties.properties,
                            responseState = responseState,
                            validations = field.validations,
                        ) {
                            handleResponseState(it)
                        }
                    }
                    is FieldProperties.NumberProperties -> {
                        NumberView(
                            properties = field.properties.properties,
                            responseState = responseState,
                            validations = field.validations,
                        ) {
                            handleResponseState(it)
                        }
                    }
                    is FieldProperties.OpinionScaleProperties -> {
                        OpinionScaleView(
                            properties = field.properties.properties,
                            responseState = responseState,
                            validations = field.validations,
                        ) {
                            handleResponseState(it)
                        }
                    }
                    is FieldProperties.RatingProperties -> {
                        RatingView(
                            properties = field.properties.properties,
                            responseState = responseState,
                            validations = field.validations,
                        ) {
                            handleResponseState(it)
                        }
                    }
                    is FieldProperties.ShortTextProperties -> {
                        ShortTextView(
                            properties = field.properties.properties,
                            responseState = responseState,
                            validations = field.validations,
                        ) {
                            handleResponseState(it)
                        }
                    }
                    is FieldProperties.StatementProperties -> {
                        // No additional content
                    }
                    is FieldProperties.YesNoProperties -> {
                        YesNoView(
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
    }
}

@PreviewLightDark
@Composable
private fun FieldViewPreview() {
    val form = Form.preview
    val field = form.fieldWithRef(Field.previewStatement.ref) ?: throw Exception("Resource Not Found")

    MaterialThemePreview {
        FieldView(
            scaffoldPadding = PaddingValues(0.dp),
            form = form,
            field = field,
            group = null,
            responses = mapOf(),
            header = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                ) {
                    Text("Intake for: Patient Name")
                }
            },
            actionHandler = { },
        )
    }
}
