package com.typeform.example.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.typeform.example.model.FormHandler
import com.typeform.example.model.RecentHandler
import com.typeform.example.ui.theme.ExampleTheme
import com.typeform.schema.Form
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.Settings
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    modifier: Modifier = Modifier,
    conclusion: Conclusion? = null,
    displayFormHandler: (Form, Settings) -> Unit = { _, _ -> },
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val recentHandler = RecentHandler(context)
    val recentIds = recentHandler.recentFormIds.collectAsState()
    val formHandler = FormHandler()

    var formId by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var skipWelcome by remember { mutableStateOf(false) }
    var skipEnding by remember { mutableStateOf(false) }
    var downloading by remember { mutableStateOf(false) }
    var downloadFailure: String? by remember { mutableStateOf(null) }
    val (focus) = FocusRequester.createRefs()

    fun download() {
        focus.freeFocus()
        downloadFailure = null

        if (formId.isBlank()) {
            return
        }

        downloading = true

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val form = formHandler.getForm(formId)
                recentHandler.addFormId(formId)
                val settings = Settings(
                    presentation = Settings.Presentation(
                        skipWelcomeScreen = skipWelcome,
                        skipEndingScreen = skipEnding,
                    )
                )
                coroutineScope.launch {
                    displayFormHandler(form, settings)
                }
            } catch (exception: FormHandler.FormException) {
                downloadFailure = when (exception) {
                    is FormHandler.FormException.Download -> {
                        "Download Failed"
                    }
                    is FormHandler.FormException.Decoding -> {
                        "Decoding Failed"
                    }
                }
            } finally {
                coroutineScope.launch {
                    downloading = false
                }
            }
        }
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
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "Preview any form by entering its unique ID.",
                    modifier = Modifier.focusRequester(focus),
                    style = MaterialTheme.typography.titleMedium,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextField(
                        value = formId,
                        onValueChange = {
                            formId = it
                        },
                        enabled = !downloading,
                        label = {
                            Text(text = "FormID")
                        }
                    )

                    TextButton(
                        onClick = {
                            expanded = true
                        },
                        modifier = Modifier.weight(1f),
                        enabled = !downloading,
                    ) {
                        Text(text = "Recent")
                    }

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
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
                        enabled = !downloading,
                    )

                    Text(text = "Skip Ending")

                    Switch(
                        checked = skipEnding,
                        onCheckedChange = {
                            skipEnding = it
                        },
                        enabled = !downloading,
                    )
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

                downloadFailure?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                Text(
                    text = "Last form conclusion:",
                    style = MaterialTheme.typography.titleMedium,
                )

                conclusion?.let {
                    when (it) {
                        is Conclusion.Completed -> {
                            Text(text = "Completed")
                        }
                        is Conclusion.Abandoned -> {
                            Text(text = "Abandoned")
                        }
                        is Conclusion.Rejected -> {
                            Text(text = "Rejected")
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

@Preview(showBackground = true)
@Composable
fun ContentPreview() {
    ExampleTheme {
        Content()
    }
}
