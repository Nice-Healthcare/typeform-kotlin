package com.typeform.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Row
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.typeform.models.Upload
import com.typeform.ui.models.Settings
import com.typeform.ui.models.UploadHelper
import com.typeform.ui.models.constructDocument
import com.typeform.ui.models.constructImage

@Composable
internal fun UploadPickerView(
    settings: Settings,
    uploadHelper: UploadHelper? = null,
    resultHandler: (Result<Upload>?) -> Unit,
) {
    val context = LocalContext.current
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

    DropdownMenuItem(
        onClick = {
            takePhoto()
        },
        enabled = uploadHelper != null,
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = Icons.Default.CameraAlt.name,
            )

            StyledTextView(
                text = settings.localization.uploadCamera,
                textStyle = MaterialTheme.typography.body1,
            )
        }
    }

    DropdownMenuItem(
        onClick = {
            photoLibraryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.PhotoLibrary,
                contentDescription = Icons.Default.PhotoLibrary.name,
            )

            StyledTextView(
                text = settings.localization.uploadPhotoLibrary,
                textStyle = MaterialTheme.typography.body1,
            )
        }
    }

    DropdownMenuItem(
        onClick = {
            documentsLauncher.launch(uploadHelper?.allowedDocumentTypes ?: emptyArray())
        },
        enabled = uploadHelper != null,
    ) {
        Row {
            Icon(
                imageVector = Icons.Default.FileUpload,
                contentDescription = Icons.Default.FileUpload.name,
            )

            StyledTextView(
                text = settings.localization.uploadDocument,
                textStyle = MaterialTheme.typography.body1,
            )
        }
    }
}
