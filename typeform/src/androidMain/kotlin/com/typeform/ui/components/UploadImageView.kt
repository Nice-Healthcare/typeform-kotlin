package com.typeform.ui.components

import android.graphics.Bitmap
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.typeform.models.Upload
import com.typeform.resources.Res
import com.typeform.resources.close_24dp
import com.typeform.resources.file_present_24dp
import com.typeform.ui.LocalSettings
import com.typeform.ui.LocalUploadHelper
import com.typeform.ui.preview.ThemePreview
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UploadImageView(
    modifier: Modifier = Modifier,
    upload: Upload,
    removeAction: () -> Unit,
) {
    val context = LocalContext.current
    val settings = LocalSettings.current
    val uploadHelper = LocalUploadHelper.current
    val padding = PaddingValues(8.dp)
    var bitmap: Bitmap? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        bitmap = uploadHelper?.bitmapForUpload(upload, context)?.getOrNull()
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        Box(
            modifier = Modifier.sizeIn(maxWidth = settings.upload.maxWidth ?: Dp.Unspecified),
            contentAlignment = Alignment.TopEnd,
        ) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
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

            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement.provides(false)) {
                IconButton(
                    onClick = {
                        removeAction()
                    },
                    modifier = Modifier
                        .background(
                            color = settings.upload.colors.backgroundColor(true).value,
                            shape = CircleShape,
                        ),
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.close_24dp),
                        contentDescription = null,
                        tint = settings.upload.colors.contentColor(true).value,
                    )
                }
            }
        }

        StyledTextView(
            text = upload.fileName,
            textStyle = MaterialTheme.typography.caption,
            modifier = Modifier.padding(start = padding.calculateLeftPadding(LayoutDirection.Ltr)),
        )
    }
}

@Composable
fun UploadPlaceholderView(padding: PaddingValues) {
    val settings = LocalSettings.current
    val glyphWidth: Dp = if (settings.upload.maxWidth != null) {
        settings.upload.maxWidth.times(0.3f)
    } else {
        60.dp
    }

    Box(
        modifier = Modifier
            .aspectRatio(1.0f)
            .padding(padding)
            .background(
                color = settings.upload.colors.backgroundColor(true).value,
                shape = settings.upload.shape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.file_present_24dp),
            contentDescription = "File Placeholder",
            modifier = Modifier.size(glyphWidth),
            tint = settings.upload.colors.contentColor(true).value,
        )
    }
}

@PreviewLightDark
@Composable
private fun UploadImageViewPreview() {
    ThemePreview {
        UploadImageView(
            modifier = Modifier.background(MaterialTheme.colors.background),
            upload = Upload(
                bytes = byteArrayOf(),
                path = Upload.Path.CAMERA,
                mimeType = "image/jpeg",
                fileName = "IMG_1234567890.jpg",
            ),
        ) { }
    }
}
