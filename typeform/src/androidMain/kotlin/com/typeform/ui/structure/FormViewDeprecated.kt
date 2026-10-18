package com.typeform.ui.structure

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import com.typeform.models.Responses
import com.typeform.schema.structure.Form
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.Settings
import com.typeform.ui.models.UploadHelper

@Deprecated(
    message = "",
    replaceWith = ReplaceWith(
        """
    FormView(
        form = form,
        skipWelcomeScreen = settings.presentation.skipWelcomeScreen,
        skipEndingScreen = settings.presentation.skipEndingScreen,
        localization = settings.localization,
        presentation = settings.presentationConfiguration,
        color = settings.colorConfiguration,
        responses = responses,
        imageLoader = imageLoader,
        uploadHelper = uploadHelper,
        header = header,
        conclusion = conclusion,
    )
    """,
    ),
)
@Composable
fun FormView(
    form: Form,
    settings: Settings = Settings(),
    responses: Responses = mapOf(),
    imageLoader: ImageLoader? = null,
    uploadHelper: UploadHelper? = null,
    conclusion: (Conclusion) -> Unit,
    header: (@Composable () -> Unit) = {},
) {
    FormView(
        form = form,
        skipWelcomeScreen = settings.presentation.skipWelcomeScreen,
        skipEndingScreen = settings.presentation.skipEndingScreen,
        localization = settings.localization,
        presentation = settings.presentationConfiguration,
        color = settings.colorConfiguration,
        responses = responses,
        imageLoader = imageLoader,
        uploadHelper = uploadHelper,
        header = header,
        conclusion = conclusion,
    )
}
