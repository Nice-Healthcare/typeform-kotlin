package com.typeform.ui.structure

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.toUri
import com.typeform.schema.structure.Attachment
import com.typeform.ui.LocalImageLoader

@Composable
internal fun AttachmentView(
    attachment: Attachment,
    modifier: Modifier = Modifier,
) {
    LocalImageLoader.current?.let { loader ->
        AsyncImage(
            model = attachment.href.toUri(),
            contentDescription = attachment.properties?.description ?: "No Image Description",
            imageLoader = loader,
            modifier = modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth,
        )
    }
}
