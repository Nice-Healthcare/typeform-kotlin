package com.typeform.example.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.typeform.TypeformDownloader
import com.typeform.example.model.RecentHandler
import com.typeform.example.ui.theme.ExampleTheme
import com.typeform.schema.Form
import com.typeform.schema.translation.merging
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentView(
    modifier: Modifier = Modifier,
    conclusion: Conclusion? = null,
    displayFormHandler: (Form, Settings) -> Unit = { _, _ -> },
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val recentHandler = RecentHandler(context)
    val recentIds = recentHandler.recentFormIds.collectAsState()
    val downloader = TypeformDownloader()

    var formId by remember { mutableStateOf("") }
    var recentExpanded by remember { mutableStateOf(false) }
    var skipWelcome by remember { mutableStateOf(false) }
    var skipEnding by remember { mutableStateOf(false) }
    var downloading by remember { mutableStateOf(false) }
    var downloadFailure: String? by remember { mutableStateOf(null) }
    var form: Form? by remember { mutableStateOf(null) }
    var language: String? by remember { mutableStateOf(null) }
    var translatedForm: Form? by remember { mutableStateOf(null) }
    var languageExpanded by remember { mutableStateOf(false) }
    val (focus) = FocusRequester.createRefs()
    val focusManager = LocalFocusManager.current

    fun download() {
        focusManager.clearFocus()
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
        val presentedForm = translatedForm ?: form ?: return

        val settings = Settings(
            presentation = Settings.Presentation(
                skipWelcomeScreen = skipWelcome,
                skipEndingScreen = skipEnding,
            )
        )

        displayFormHandler(presentedForm, settings)
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
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
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Text(
                    text = "Retrieve",
                    style = MaterialTheme.typography.titleMedium,
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            TextField(
                                value = formId,
                                onValueChange = {
                                    formId = it
                                },
                                modifier = Modifier
                                    .focusRequester(focus),
                                keyboardOptions = KeyboardOptions(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = {
                                        download()
                                    },
                                ),
                                enabled = !downloading,
                                label = {
                                    Text(text = "FormID")
                                }
                            )

                            TextButton(
                                onClick = {
                                    recentExpanded = true
                                },
                                modifier = Modifier.weight(1f),
                                enabled = !downloading,
                            ) {
                                Text(text = "Recent")
                            }

                            DropdownMenu(
                                expanded = recentExpanded,
                                onDismissRequest = {
                                    recentExpanded = false
                                }
                            ) {
                                recentIds.value.forEach {
                                    DropdownMenuItem(
                                        text = {
                                            Text(text = it)
                                        },
                                        onClick = {
                                            formId = it
                                            download()
                                        }
                                    )
                                }

                                DropdownMenuItem(
                                    text = {
                                        Text(text = "Clear")
                                    },
                                    onClick = {
                                        recentHandler.clear()
                                    }
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Button(
                                onClick = {
                                    download()
                                },
                                enabled = !downloading,
                            ) {
                                Text(text = "Download")
                            }

                            if (downloading) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }

                Text(
                    text = "Preview any form by entering its unique ID.",
                    style = MaterialTheme.typography.labelSmall,
                )

                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleMedium,
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "Skip Welcome")

                            Switch(
                                checked = skipWelcome,
                                onCheckedChange = {
                                    skipWelcome = it
                                },
                                enabled = form != null,
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "Skip Ending")

                            Switch(
                                checked = skipEnding,
                                onCheckedChange = {
                                    skipEnding = it
                                },
                                enabled = form != null,
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = "Language")

                            TextButton(
                                onClick = {
                                    languageExpanded = true
                                },
                                modifier = Modifier.weight(1f),
                                enabled = form != null,
                            ) {
                                Text(text = language ?: "Default ${form?.settings?.language ?: "N/A"}")
                            }

                            DropdownMenu(
                                expanded = languageExpanded,
                                onDismissRequest = {
                                    languageExpanded = false
                                }
                            ) {
                                DropdownMenuItem(
                                    text = {
                                        Text(text = language ?: "Default ${form?.settings?.language ?: "N/A"}")
                                    },
                                    onClick = {
                                        language = null
                                        translatedForm = null
                                    }
                                )

                                form?.settings?.translation_languages?.let { languages ->
                                    languages.forEach { languageCode ->
                                        DropdownMenuItem(
                                            text = {
                                                Text(text = languageCode)
                                            },
                                            onClick = {
                                                language = languageCode
                                                downloadTranslation()
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Button(
                                onClick = {
                                    present()
                                },
                                enabled = form != null,
                            ) {
                                Text(text = "Present")
                            }

                            if (downloading) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                }

                downloadFailure?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Text(
                    text = "Conclusion",
                    style = MaterialTheme.typography.titleMedium,
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    conclusion?.let {
                        when (it) {
                            is Conclusion.Completed -> {
                                Text(text = "Completed")

                                ResponsesView(responses = it.responses)
                            }
                            is Conclusion.Abandoned -> {
                                Text(text = "Abandoned")

                                ResponsesView(responses = it.responses)
                            }
                            is Conclusion.Rejected -> {
                                Text(text = "Rejected")

                                ResponsesView(responses = it.responses)
                            }
                            is Conclusion.Canceled -> {
                                Text(text = "Canceled")
                            }
                        }
                    }
                }
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
