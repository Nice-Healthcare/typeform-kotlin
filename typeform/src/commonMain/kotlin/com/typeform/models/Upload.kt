package com.typeform.models

import com.typeform.serializers.UploadPathSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Upload(
    val bytes: ByteArray,
    val path: Path,
    val mimeType: String,
    val fileName: String,
) {
    @Serializable(with = UploadPathSerializer::class)
    enum class Path(
        val rawValue: String,
    ) {
        CAMERA("camera"),
        DOCUMENTS("documents"),
        PHOTO_LIBRARY("photoLibrary"),
    }

    companion object
}
