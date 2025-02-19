package com.typeform.example.model

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.FileProvider
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.shouldShowRationale
import com.typeform.models.Upload
import com.typeform.ui.models.UploadHelper
import java.io.File
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ExampleUploadHelper @OptIn(ExperimentalPermissionsApi::class) constructor(
    val cameraPermissionState: PermissionState
) : UploadHelper {
    override val allowedDocumentTypes: Array<String>
        get() = arrayOf(
            "image/jpeg",
            "image/png",
            "application/pdf",
        )

    @OptIn(ExperimentalUuidApi::class, ExperimentalPermissionsApi::class)
    override fun uriForCameraImage(context: Context): Result<Uri?> {
        when (cameraPermissionState.status) {
            is PermissionStatus.Denied -> {
                if (cameraPermissionState.status.shouldShowRationale) {
                    return Result.failure(Exception("Camera Permission Denied"))
                } else {
                    cameraPermissionState.launchPermissionRequest()
                    return Result.success(null)
                }
            }
            is PermissionStatus.Granted -> {
                try {
                    val directory = File(context.cacheDir, "images")
                    directory.mkdirs()
                    val file = File("${directory}/${Uuid.random()}.jpg")
                    val uri = FileProvider.getUriForFile(context, context.packageName, file)
                    return Result.success(uri)
                } catch (exception: Exception) {
                    return Result.failure(exception)
                }
            }
        }
    }

    override fun bitmapForUpload(upload: Upload, context: Context): Result<Bitmap> {
        val bitmap = BitmapFactory.decodeByteArray(upload.bytes, 0, upload.bytes.size)
        return if (bitmap != null) {
            Result.success(bitmap)
        } else {
            Result.failure(Exception("Bitmap Failed"))
        }
    }
}
