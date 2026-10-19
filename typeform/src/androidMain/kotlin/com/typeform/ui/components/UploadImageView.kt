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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.typeform.models.Upload
import com.typeform.resources.Res
import com.typeform.resources.close_24dp
import com.typeform.resources.file_present_24dp
import com.typeform.ui.LocalColors
import com.typeform.ui.LocalPresentation
import com.typeform.ui.LocalUploadHelper
import com.typeform.ui.models.Script
import com.typeform.ui.preview.MaterialThemePreview
import org.jetbrains.compose.resources.vectorResource

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun UploadImageView(
    modifier: Modifier = Modifier,
    upload: Upload,
    removeAction: () -> Unit,
) {
    val context = LocalContext.current
    val colors = LocalColors.current
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
            modifier = Modifier.sizeIn(maxWidth = 200.dp),
            contentAlignment = Alignment.TopEnd,
        ) {
            when (val image = bitmap) {
                null -> {
                    UploadPlaceholderView(
                        modifier = Modifier.padding(padding),
                    )
                }
                else -> {
                    Image(
                        bitmap = image.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Fit,
                    )
                }
            }

            CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement.provides(false)) {
                IconButton(
                    onClick = {
                        removeAction()
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = colors.buttonColors.containerColor,
                        contentColor = colors.buttonColors.contentColor,
                    ),
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.close_24dp),
                        contentDescription = null,
                    )
                }
            }
        }

        TextView(
            modifier = Modifier.padding(start = padding.calculateLeftPadding(LayoutDirection.Ltr)),
            text = upload.fileName,
            script = Script.LABEL,
        )
    }
}

@Composable
internal fun UploadPlaceholderView(modifier: Modifier = Modifier) {
    val presentation = LocalPresentation.current
    val colors = LocalColors.current

    Box(
        modifier = modifier
            .aspectRatio(1.0f)
            .background(
                color = colors.outlinedButton.unselectedContainerColor,
                shape = presentation.buttonShape,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = vectorResource(Res.drawable.file_present_24dp),
            contentDescription = "File Placeholder",
            modifier = Modifier.size(60.dp),
            tint = colors.outlinedButton.selectedStrokeColor,
        )
    }
}

@PreviewLightDark
@Composable
private fun UploadImageViewPreview() {
    MaterialThemePreview {
        UploadImageView(
            upload = Upload(
                bytes = byteArrayOf(),
                path = Upload.Path.CAMERA,
                mimeType = "image/jpeg",
                fileName = "IMG_1234567890.jpg",
            ),
        ) { }
    }
}
