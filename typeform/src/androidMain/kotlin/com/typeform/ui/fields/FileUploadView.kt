package com.typeform.ui.fields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.models.ResponseValue
import com.typeform.models.Upload
import com.typeform.schema.questions.FileUpload
import com.typeform.schema.structure.Validations
import com.typeform.ui.LocalLocalization
import com.typeform.ui.LocalPresentation
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.components.UploadImageView
import com.typeform.ui.components.UploadPickerView
import com.typeform.ui.models.ResponseState
import com.typeform.ui.preview.MaterialThemePreview

@Composable
internal fun FileUploadView(
    properties: FileUpload,
    responseState: ResponseState,
    validations: Validations?,
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
        verticalArrangement = Arrangement.spacedBy(LocalPresentation.current.contentVerticalSpacing),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        properties.description?.let {
            StyledTextView(
                text = it,
                textStyle = MaterialTheme.typography.labelMedium,
            )
        }

        if (upload != null) {
            UploadImageView(
                upload = upload!!,
            ) {
                select(null)
            }
        } else {
            OutlinedButton(
                onClick = {
                    expanded = true
                },
                modifier = Modifier.fillMaxWidth(),
                contentPadding = LocalPresentation.current.containerPadding,
            ) {
                StyledTextView(
                    text = LocalLocalization.current.uploadAction,
                    textStyle = MaterialTheme.typography.bodyMedium,
                )
            }
        }

        exception?.let {
            StyledTextView(
                text = it.message ?: "An error occurred.",
                textStyle = MaterialTheme.typography.labelMedium,
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
        ) {
            UploadPickerView { result ->
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

@PreviewLightDark
@Composable
private fun FileUploadViewPreview() {
    MaterialThemePreview {
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(MaterialTheme.colorScheme.background),
        ) {
            FileUploadView(
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
