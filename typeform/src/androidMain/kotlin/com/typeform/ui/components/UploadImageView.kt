package com.typeform.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.typeform.models.Upload
import com.typeform.resources.Res
import com.typeform.resources.close_24dp
import com.typeform.resources.file_present_24dp
import com.typeform.ui.LocalUploadHelper
import com.typeform.ui.models.imageBitmapForUpload
import com.typeform.ui.preview.MaterialThemePreview
import org.jetbrains.compose.resources.vectorResource

@Composable
fun UploadImageView(
    modifier: Modifier = Modifier,
    upload: Upload,
    removeAction: () -> Unit,
) {
    val context = LocalContext.current
    val uploadHelper = LocalUploadHelper.current
    val padding = PaddingValues(8.dp)
    var bitmap: ImageBitmap? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        bitmap = uploadHelper?.imageBitmapForUpload(upload, context)?.getOrNull()
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        Box(
            modifier = Modifier.sizeIn(maxWidth = 200.dp),
            contentAlignment = Alignment.TopEnd,
        ) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap!!,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Fit,
                )
            } else {
                UploadPlaceholderView(
                    padding = padding,
                )
            }

            CompositionLocalProvider(LocalMinimumInteractiveComponentSize.provides(44.dp)) {
                IconButton(
                    onClick = {
                        removeAction()
                    },
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.close_24dp),
                        contentDescription = null,
                    )
                }
            }
        }

        StyledTextView(
            text = upload.fileName,
            textStyle = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(start = padding.calculateLeftPadding(LayoutDirection.Ltr)),
        )
    }
}

@Composable
fun UploadPlaceholderView(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .aspectRatio(1.0f)
            .padding(padding),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.file_present_24dp),
            contentDescription = "File Placeholder",
            modifier = Modifier.size(60.dp),
        )
    }
}

@PreviewLightDark
@Composable
private fun UploadImageViewPreview() {
    MaterialThemePreview {
        UploadImageView(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            upload = Upload(
                bytes = byteArrayOf(),
                path = Upload.Path.CAMERA,
                mimeType = "image/jpeg",
                fileName = "IMG_1234567890.jpg",
            ),
        ) { }
    }
}
