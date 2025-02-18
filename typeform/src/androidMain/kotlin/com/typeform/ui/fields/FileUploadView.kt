package com.typeform.ui.fields

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.DropdownMenu
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
import com.typeform.models.Upload
import com.typeform.schema.FileUpload
import com.typeform.schema.Validations
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.components.UploadPickerView
import com.typeform.ui.models.ResponseState
import com.typeform.ui.models.Settings
import com.typeform.ui.preview.ThemePreview

@Composable
internal fun FileUploadView(
    settings: Settings,
    properties: FileUpload,
    responseState: ResponseState,
    validations: Validations?,
    stateHandler: (ResponseState) -> Unit,
) {
    var value: Upload? by remember { mutableStateOf(responseState.response?.asUpload()) }
    var expanded: Boolean by remember { mutableStateOf(false) }
    var path: Upload.Path? by remember { mutableStateOf(null) }
    var exception: Throwable? by remember { mutableStateOf(null) }

    fun updateState() {
    }

    fun selectFile() {
    }

    LaunchedEffect(Unit) {
        updateState()
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(settings.presentation.contentVerticalSpacing),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                expanded = true
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            StyledTextView(
                text = settings.localization.uploadAction,
                textStyle = MaterialTheme.typography.body1,
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
            ) { result ->
                expanded = false
                result?.fold(
                    onSuccess = {
                        value = it
                    },
                    onFailure = {
                        exception = it
                    }
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
                    description = "Select a file."
                ),
                responseState = ResponseState(),
                validations = null,
            ) {
            }
        }
    }
}
