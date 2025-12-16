package com.typeform.example.ui.content

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.SingletonImageLoader
import coil3.compose.LocalPlatformContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.typeform.TypeformDownloader
import com.typeform.example.model.ExampleUploadHelper
import com.typeform.example.model.RecentHandler
import com.typeform.example.ui.theme.ExampleTheme
import com.typeform.schema.structure.Form
import com.typeform.schema.translation.merging
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.Settings
import com.typeform.ui.structure.FormView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun ContentView(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false,
        )
    )
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    val recentHandler = RecentHandler(context)
    val downloader = TypeformDownloader()

    var formId by remember { mutableStateOf("") }
    var downloading by remember { mutableStateOf(false) }
    var downloadFailure: String? by remember { mutableStateOf(null) }
    var form: Form? by remember { mutableStateOf(null) }
    var presentingForm: Form? by remember { mutableStateOf(null) }
    var settings: Settings by remember { mutableStateOf(Settings()) }
    var conclusion: Conclusion? by remember { mutableStateOf(null) }
    var language: String? by remember { mutableStateOf(null) }
    var translatedForm: Form? by remember { mutableStateOf(null) }

    fun download() {
        downloadFailure = null

        if (formId.isBlank()) {
            return
        }

        downloading = true

        CoroutineScope(Dispatchers.IO).launch {
            val result = downloader.downloadForm(formId)
            try {
                form = result.getOrThrow()
                recentHandler.addFormId(formId)
            } catch (exception: Exception) {
                downloadFailure = "Download Failed: $exception"
            } finally {
                coroutineScope.launch {
                    downloading = false
                }
            }
        }
    }

    fun downloadTranslation() {
        val languageCode = language ?: return

        downloading = true

        CoroutineScope(Dispatchers.IO).launch {
            val result = downloader.downloadTranslation(languageCode, formId)
            try {
                val translation = result.getOrThrow()
                coroutineScope.launch {
                    translatedForm = form?.merging(translation)
                }
            } catch (exception: Exception) {
                downloadFailure = "Download Failed: $exception"
            } finally {
                coroutineScope.launch {
                    downloading = false
                }
            }
        }
    }

    fun present() {
        presentingForm = translatedForm ?: form ?: return
        coroutineScope.launch {
            scaffoldState.bottomSheetState.expand()
        }
    }

    fun conclude(with: Conclusion) {
        conclusion = with
        coroutineScope.launch {
            scaffoldState.bottomSheetState.hide()
            presentingForm = null
        }
    }

    BottomSheetScaffold(
        sheetContent = {
            presentingForm?.let { form ->
                FormView(
                    form = form,
                    settings = settings,
                    imageLoader = SingletonImageLoader.get(LocalPlatformContext.current),
                    uploadHelper = ExampleUploadHelper(
                        cameraPermissionState = cameraPermissionState,
                    ),
                    conclusion = {
                        conclude(it)
                    }
                )
            }
        },
        modifier = modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = false,
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Typeform")
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                downloadFailure?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                RetrieveView(
                    formId = formId,
                    recentFormIds = recentHandler.recentFormIds,
                    enabled = !downloading,
                    onClearRecents = {
                        recentHandler.clear()
                    },
                    onChangeFormId = {
                        formId = it
                    },
                ) {
                    download()
                }

                SettingsView(
                    settings = settings,
                    language = language,
                    languagesAvailable = form?.settings?.translation_languages,
                    enabled = form != null,
                    onLanguageChange = {
                        language = it
                        if (it != null) {
                            downloadTranslation()
                        } else {
                            translatedForm = null
                        }
                    },
                    onSettingsChange = {
                        settings = it
                    }
                ) {
                    present()
                }

                ConclusionView(
                    conclusion = conclusion,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentViewPreview() {
    ExampleTheme {
        ContentView()
    }
}
