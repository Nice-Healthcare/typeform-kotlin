package com.typeform.ui.fields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.models.ResponseValue
import com.typeform.models.Upload
import com.typeform.schema.questions.FileUpload
import com.typeform.schema.structure.Validations
import com.typeform.ui.LocalLocalization
import com.typeform.ui.LocalPresentation
import com.typeform.ui.components.ContentContainerView
import com.typeform.ui.components.TextView
import com.typeform.ui.components.UploadImageView
import com.typeform.ui.components.UploadPickerView
import com.typeform.ui.models.Appearance
import com.typeform.ui.models.ResponseState
import com.typeform.ui.preview.TypeformPreview

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

    ContentContainerView(
        description = properties.description,
    ) {
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
                TextView(
                    text = LocalLocalization.current.uploadAction,
                    typeStyle = Appearance.TypeStyle.TITLE,
                )
            }
        }

        exception?.let {
            TextView(
                text = it.message ?: "An error occurred.",
                typeStyle = Appearance.TypeStyle.BODY,
            )
        }

        UploadPickerView(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
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

@PreviewLightDark
@Composable
private fun FileUploadViewPreview() {
    TypeformPreview(
        headline = "Share a file?",
    ) {
        FileUploadView(
            properties = FileUpload(
                description = "Provide additional documents or photos.",
            ),
            responseState = ResponseState(),
            validations = null,
        ) {
        }
    }
}
