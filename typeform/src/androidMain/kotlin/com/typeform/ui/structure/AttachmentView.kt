package com.typeform.ui.structure

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.toUri
import com.typeform.schema.Attachment

@Composable
internal fun AttachmentView(
    attachment: Attachment,
    modifier: Modifier = Modifier,
    imageLoader: ImageLoader? = null,
) {
    imageLoader?.let { loader ->
        AsyncImage(
            model = attachment.href.toString().toUri(),
            contentDescription = attachment.properties?.description ?: "No Image Description",
            imageLoader = loader,
            modifier = modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )
    }
}
