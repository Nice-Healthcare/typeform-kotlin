package com.typeform.ui.components

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import androidx.core.database.getStringOrNull
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.typeform.models.Upload
import com.typeform.ui.models.Settings
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPermissionsApi::class, ExperimentalUuidApi::class)
@Composable
internal fun UploadPickerView(
    settings: Settings,
    resultHandler: (Result<Upload>?) -> Unit
) {
    val context = LocalContext.current
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    var photoUri: Uri? by remember { mutableStateOf(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
    ) { success ->
        if (success) {
            if (photoUri == null) {
                resultHandler(null)
            } else {
                val upload = Upload.constructImage(
                    uri = photoUri!!,
                    path = Upload.Path.CAMERA,
                    context = context
                )
                resultHandler(upload)
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
            val upload = Upload.constructImage(
                uri = uri,
                path = Upload.Path.PHOTO_LIBRARY,
                context = context
            )
            resultHandler(upload)
        }
    }

    val documentsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
    ) { uri ->
        if (uri == null) {
            resultHandler(null)
        } else {
            val upload = Upload.constructDocument(uri, context)
            resultHandler(upload)
        }
    }

    fun takePhoto() {
        when (cameraPermissionState.status) {
            is PermissionStatus.Denied -> {
                if ((cameraPermissionState.status as PermissionStatus.Denied).shouldShowRationale) {
                    resultHandler(Result.failure(Exception("Camera Permission Denied")))
                } else {
                    cameraPermissionState.launchPermissionRequest()
                }
            }
            is PermissionStatus.Granted -> {
                try {
                    val path = Uuid.random().toString().plus(".jpg")
                    val file = File(context.cacheDir, path)
                    val uri = FileProvider.getUriForFile(context, context.packageName, file)
                    photoUri = uri
                    cameraLauncher.launch(uri)
                } catch (exception: Exception) {
                    resultHandler(Result.failure(exception))
                }
            }
        }
    }

    DropdownMenuItem(
        text = {
            StyledTextView(
                text = settings.localization.uploadCamera,
                textStyle = MaterialTheme.typography.body1,
            )
        },
        onClick = {
            takePhoto()
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = Icons.Default.CameraAlt.name,
            )
        }
    )

    DropdownMenuItem(
        text = {
            StyledTextView(
                text = settings.localization.uploadPhotoLibrary,
                textStyle = MaterialTheme.typography.body1,
            )
        },
        onClick = {
            photoLibraryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.PhotoLibrary,
                contentDescription = Icons.Default.PhotoLibrary.name,
            )
        }
    )

    DropdownMenuItem(
        text = {
            StyledTextView(
                text = settings.localization.uploadDocument,
                textStyle = MaterialTheme.typography.body1,
            )
        },
        onClick = {
            documentsLauncher.launch(listOf(
                "image/jpeg",
                "image/png",
                "application/pdf",
            ).toTypedArray())
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.FileUpload,
                contentDescription = Icons.Default.FileUpload.name,
            )
        }
    )
}

fun Upload.Companion.constructImage(
    uri: Uri,
    path: Upload.Path,
    context: Context,
): Result<Upload> {
    val contentResolver = context.contentResolver

    var bytes = runBlocking {
        withContext(Dispatchers.IO) {
            contentResolver.openInputStream(uri).use {
                it?.readBytes()
            }
        }
    } ?: return Result.failure(Exception("Could not read file."))

    val inputStream = ByteArrayInputStream(bytes)
    val bitmap = BitmapFactory.decodeStream(inputStream) ?: return Result.failure(Exception("Could not read bitmap."))
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
    val uploadBytes = outputStream.toByteArray()

    val upload = Upload(
        bytes = uploadBytes,
        path = path,
        mimeType = "image/jpeg",
        fileName = uri.path ?: "New Image.jpg"
    )

    return Result.success(upload)
}

fun Upload.Companion.constructDocument(
    uri: Uri,
    context: Context,
): Result<Upload> {
    val contentResolver = context.contentResolver

    val contentType = contentResolver.getType(uri)
        ?: return Result.failure(Exception("Could not read content type."))

    if (contentType != "application/pdf") {
        return constructImage(uri, Upload.Path.DOCUMENTS, context)
    }

    val query = contentResolver.query(
        uri,
        arrayOf(OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE),
        null,
        null,
    )

    if (query == null) {
        return Result.failure(Exception("Could not read file name."))
    }

    val fileName = query.use { cursor ->
        cursor.moveToFirst()
        cursor.getStringOrNull(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)) ?: "New Document"
    }

    var bytes = runBlocking {
        withContext(Dispatchers.IO) {
            contentResolver.openInputStream(uri).use {
                it?.readBytes()
            }
        }
    } ?: return Result.failure(Exception("Could not read file."))

    val upload = Upload(
        bytes = bytes,
        path = Upload.Path.DOCUMENTS,
        mimeType = contentType,
        fileName = fileName,
    )

    return Result.success(upload)
}
