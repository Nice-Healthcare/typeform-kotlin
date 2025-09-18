package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.DropdownMenu
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.typeform.models.ResponseValue
import com.typeform.models.Upload
import com.typeform.schema.Validations
import com.typeform.schema.questions.FileUpload
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.components.UploadImageView
import com.typeform.ui.components.UploadPickerView
import com.typeform.ui.models.ResponseState
import com.typeform.ui.models.Settings
import com.typeform.ui.models.UploadHelper
import com.typeform.ui.preview.ThemePreview

@Composable
internal fun FileUploadView(
    settings: Settings,
    properties: FileUpload,
    responseState: ResponseState,
    validations: Validations?,
    uploadHelper: UploadHelper? = null,
    stateHandler: (ResponseState) -> Unit,
) {
    var upload: Upload? by remember { mutableStateOf(responseState.response?.asUpload()) }
    var expanded: Boolean by remember { mutableStateOf(false) }
    var exception: Throwable? by remember { mutableStateOf(null) }

    fun updateState() {
        var state = responseState

        state = if (upload != null) {
            state.copy(response = ResponseValue.UploadValue(upload!!))
        } else {
            state.copy(response = null)
        }

        state = if (validations != null && validations.required) {
            state.copy(invalid = state.response == null)
        } else {
            state.copy(invalid = false)
        }

        stateHandler(state)
    }

    fun select(file: Upload?) {
        upload = file

        updateState()
    }

    LaunchedEffect(Unit) {
        updateState()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(settings.presentation.contentVerticalSpacing),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (upload != null) {
            UploadImageView(
                upload = upload!!,
                settings = settings,
                uploadHelper = uploadHelper,
            ) {
                select(null)
            }
        } else {
            OutlinedButton(
                onClick = {
                    expanded = true
                },
                modifier = Modifier.fillMaxWidth(),
                elevation = null,
                shape = settings.outlinedButton.shape,
                border = settings.outlinedButton.border,
                colors = settings.outlinedButton.colors,
                contentPadding = settings.outlinedButton.contentPadding,
            ) {
                StyledTextView(
                    text = settings.localization.uploadAction,
                    textStyle = MaterialTheme.typography.body1,
                )
            }
        }

        exception?.let {
            StyledTextView(
                text = it.message ?: "An error occurred.",
                textStyle = MaterialTheme.typography.subtitle1,
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            UploadPickerView(
                settings = settings,
                uploadHelper = uploadHelper,
            ) { result ->
                expanded = false
                result?.fold(
                    onSuccess = {
                        select(it)
                    },
                    onFailure = {
                        exception = it
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FileUploadViewPreview() {
    ThemePreview {
        Box(
            modifier = Modifier.size(200.dp),
        ) {
            FileUploadView(
                settings = Settings(),
                properties = FileUpload(
                    description = "Select a file.",
                ),
                responseState = ResponseState(),
                validations = null,
            ) {
            }
        }
    }
}
