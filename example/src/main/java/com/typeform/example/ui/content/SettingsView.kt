package com.typeform.example.ui.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.example.ui.theme.ExampleTheme
import com.typeform.ui.models.Appearance

@Composable
fun SettingsView(
    modifier: Modifier = Modifier,
    appearance: Appearance,
    language: String? = null,
    languagesAvailable: List<String>? = null,
    enabled: Boolean = true,
    onLanguageChange: (String?) -> Unit = { },
    onAppearanceChange: (Appearance) -> Unit = { },
    onPresent: () -> Unit,
) {
    var languageExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = modifier
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(
                text = "Settings",
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleLarge,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Skip Welcome")

                Switch(
                    checked = appearance.additionalLogic.skipWelcomeScreen,
                    onCheckedChange = {
                        val update = appearance.copy(
                            additionalLogic = appearance.additionalLogic.copy(
                                skipWelcomeScreen = it
                            )
                        )
                        onAppearanceChange(update)
                    },
                    enabled = enabled,
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Skip Ending")

                Switch(
                    checked = appearance.additionalLogic.skipEndingScreen,
                    onCheckedChange = {
                        val update = appearance.copy(
                            additionalLogic = appearance.additionalLogic.copy(
                                skipEndingScreen = it
                            )
                        )
                        onAppearanceChange(update)
                    },
                    enabled = enabled,
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
                    enabled = enabled,
                ) {
                    Text(text = language ?: "Default ${language ?: "N/A"}")
                }

                DropdownMenu(
                    expanded = languageExpanded,
                    onDismissRequest = {
                        languageExpanded = false
                    }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = language ?: "Default ${language ?: "N/A"}")
                        },
                        onClick = {
                            onLanguageChange(null)
                        }
                    )

                    languagesAvailable?.let { languages ->
                        languages.forEach { languageCode ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = languageCode)
                                },
                                onClick = {
                                    onLanguageChange(languageCode)
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
                        onPresent()
                    },
                    enabled = enabled,
                ) {
                    Text(text = "Present")
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun SettingsViewPreview() {
    var appearance: Appearance by remember { mutableStateOf(Appearance()) }
    ExampleTheme {
        SettingsView(
            appearance = appearance,
            onAppearanceChange = {
                appearance = it
            }
        ) {
        }
    }
}
