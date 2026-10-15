package com.typeform.ui.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.database.getStringOrNull
import com.typeform.models.Upload
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

interface UploadHelper {
    /**
     * Collection of MIME types that are allowed to be selected for document uploads.
     */
    val allowedDocumentTypes: Array<String>

    /**
     * Provide a [Uri] that can be written to by the device hardware camera.
     *
     * Each device is required to handle hardware permissions as needed.
     */
    fun uriForCameraImage(context: Context): Result<Uri?>

    /**
     * Generate a preview/thumbnail [Bitmap] for the provided [Upload].
     */
    fun bitmapForUpload(
        upload: Upload,
        context: Context,
    ): Result<Bitmap>
}

fun UploadHelper.constructImage(
    uri: Uri,
    path: Upload.Path,
    context: Context,
): Result<Upload> {
    val contentResolver = context.contentResolver

    val bytes = runBlocking {
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
        fileName = uri.lastPathSegment ?: "New Image.jpg",
    )

    return Result.success(upload)
}

fun UploadHelper.constructDocument(
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

    val bytes = runBlocking {
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

fun UploadHelper.imageBitmapForUpload(
    upload: Upload,
    context: Context,
): Result<ImageBitmap> {
    val bitmapResult = bitmapForUpload(upload, context)
    return try {
        val bitmap = bitmapResult.getOrThrow()
        Result.success(bitmap.asImageBitmap())
    } catch (exception: Exception) {
        Result.failure(exception)
    }
}
