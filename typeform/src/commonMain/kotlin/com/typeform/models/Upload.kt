package com.typeform.models

data class Upload(
    val bytes: ByteArray,
    val path: Path,
    val mimeType: String,
    val fileName: String,
) {
    enum class Path(val rawValue: String) {
        CAMERA("camera"),
        PHOTO_LIBRARY("photoLibrary"),
        DOCUMENTS("documents"),
    }

    companion object
}
