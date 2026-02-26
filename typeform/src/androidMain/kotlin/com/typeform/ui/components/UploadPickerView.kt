package com.typeform.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.typeform.models.Upload
import com.typeform.resources.Res
import com.typeform.resources.camera_fill_24dp
import com.typeform.resources.photo_library_fill_24dp
import com.typeform.resources.upload_file_fill_24dp
import com.typeform.ui.LocalLocalization
import com.typeform.ui.LocalUploadHelper
import com.typeform.ui.models.Appearance
import com.typeform.ui.models.constructDocument
import com.typeform.ui.models.constructImage
import com.typeform.ui.preview.MaterialThemePreview
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun UploadPickerView(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    resultHandler: (Result<Upload>?) -> Unit,
) {
    val context = LocalContext.current
    val uploadHelper = LocalUploadHelper.current
    var photoUri: Uri? by remember { mutableStateOf(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { success ->
        if (success) {
            if (photoUri == null) {
                resultHandler(null)
            } else {
                if (uploadHelper != null) {
                    val upload = uploadHelper.constructImage(
                        uri = photoUri!!,
                        path = Upload.Path.CAMERA,
                        context = context,
                    )
                    resultHandler(upload)
                } else {
                    resultHandler(null)
                }
            }
        } else {
            resultHandler(null)
        }
    }

    val photoLibraryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) { uri ->
        if (uri == null) {
            resultHandler(null)
        } else {
            if (uploadHelper != null) {
                val upload = uploadHelper.constructImage(
                    uri = uri,
                    path = Upload.Path.PHOTO_LIBRARY,
                    context = context,
                )
                resultHandler(upload)
            } else {
                resultHandler(null)
            }
        }
    }

    val documentsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
    ) { uri ->
        if (uri == null) {
            resultHandler(null)
        } else {
            if (uploadHelper != null) {
                val upload = uploadHelper.constructDocument(uri, context)
                resultHandler(upload)
            } else {
                resultHandler(null)
            }
        }
    }

    fun takePhoto() {
        if (uploadHelper != null) {
            try {
                val result = uploadHelper.uriForCameraImage(context)
                val uri = result.getOrThrow()
                photoUri = uri
                uri?.let {
                    cameraLauncher.launch(it)
                }
            } catch (exception: Exception) {
                resultHandler(Result.failure(exception))
            }
        }
    }

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
    ) {
        DropdownMenuItem(
            text = {
                Row {
                    Icon(
                        imageVector = vectorResource(Res.drawable.camera_fill_24dp),
                        contentDescription = "Camera",
                    )

                    TextView(
                        text = LocalLocalization.current.uploadCamera,
                        typeStyle = Appearance.TypeStyle.TITLE,
                    )
                }
            },
            onClick = {
                takePhoto()
            },
            enabled = uploadHelper != null,
        )

        DropdownMenuItem(
            text = {
                Row {
                    Icon(
                        imageVector = vectorResource(Res.drawable.photo_library_fill_24dp),
                        contentDescription = "Photo Library",
                    )

                    TextView(
                        text = LocalLocalization.current.uploadPhotoLibrary,
                        typeStyle = Appearance.TypeStyle.TITLE,
                    )
                }
            },
            onClick = {
                photoLibraryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            },
        )

        DropdownMenuItem(
            text = {
                Row {
                    Icon(
                        imageVector = vectorResource(Res.drawable.upload_file_fill_24dp),
                        contentDescription = "Upload File",
                    )

                    TextView(
                        text = LocalLocalization.current.uploadDocument,
                        typeStyle = Appearance.TypeStyle.TITLE,
                    )
                }
            },
            onClick = {
                documentsLauncher.launch(uploadHelper?.allowedDocumentTypes ?: emptyArray())
            },
            enabled = uploadHelper != null,
        )
    }
}

@PreviewLightDark
@Composable
private fun UploadPickerViewPreview() {
    MaterialThemePreview {
        UploadPickerView(
            expanded = true,
            onDismissRequest = {
            },
        ) { _ ->
        }
    }
}
