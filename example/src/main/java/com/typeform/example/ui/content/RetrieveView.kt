package com.typeform.example.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.FocusRequesterFactory.component1
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.example.ui.theme.ExampleTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun RetrieveView(
    modifier: Modifier = Modifier,
    formId: String,
    recentFormIds: StateFlow<Set<String>>,
    enabled: Boolean = true,
    onClearRecents: () -> Unit = { },
    onChangeFormId: (String) -> Unit = { },
    onDownload: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val (focus) = FocusRequester.createRefs()
    val recentIds = recentFormIds.collectAsState()
    var recentExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Text(
                text = "Retrieve",
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = "Preview any form by entering its unique ID.",
                style = MaterialTheme.typography.labelSmall,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    value = formId,
                    onValueChange = {
                        onChangeFormId(it)
                    },
                    modifier = Modifier
                        .focusRequester(focus),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            onDownload()
                        },
                    ),
                    enabled = enabled,
                    label = {
                        Text(text = "FormID")
                    }
                )

                Box(
                    modifier = Modifier
                        .weight(1f),
                ) {
                    TextButton(
                        onClick = {
                            recentExpanded = true
                        },
                        enabled = enabled,
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Text(text = "Recent")
                    }

                    DropdownMenu(
                        expanded = recentExpanded,
                        onDismissRequest = {
                            recentExpanded = false
                            focusManager.clearFocus()
                        }
                    ) {
                        recentIds.value.forEach {
                            DropdownMenuItem(
                                text = {
                                    Text(text = it)
                                },
                                onClick = {
                                    recentExpanded = false
                                    focusManager.clearFocus()
                                    onChangeFormId(it)
                                    onDownload()
                                }
                            )
                        }

                        DropdownMenuItem(
                            text = {
                                Text(text = "Clear")
                            },
                            onClick = {
                                onClearRecents()
                            }
                        )
                    }
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Button(
                    onClick = {
                        onDownload()
                        focusManager.clearFocus()
                    },
                    enabled = enabled,
                ) {
                    Text(text = "Download")
                }

                if (!enabled) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun RetrieveViewPreview() {
    var formId: String by remember { mutableStateOf("") }
    ExampleTheme {
        RetrieveView(
            formId = formId,
            recentFormIds = MutableStateFlow(emptySet()),
            onChangeFormId = {
                formId = it
            },
        ) {
        }
    }
}
