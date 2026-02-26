package com.typeform.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.resources.Res
import com.typeform.resources.arrow_back_24dp
import com.typeform.ui.LocalLocalization
import com.typeform.ui.LocalPresentation
import com.typeform.ui.models.Appearance
import com.typeform.ui.preview.MaterialThemePreview
import org.jetbrains.compose.resources.vectorResource

@Composable
internal fun TopNavigationBar(
    modifier: Modifier = Modifier,
    showBackNavigation: Boolean = false,
    onBack: () -> Unit,
    onExit: () -> Unit,
) {
    val presentation = LocalPresentation.current
    val localization = LocalLocalization.current

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(presentation.topBarPadding),
            ) {
                Row(
                    modifier = Modifier.heightIn(min = 48.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (showBackNavigation) {
                        IconButton(
                            onClick = {
                                onBack()
                            },
                        ) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.arrow_back_24dp),
                                contentDescription = "Navigate back",
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.weight(1f),
                    )

                    TextButton(
                        onClick = {
                            onExit()
                        },
                    ) {
                        TextView(
                            text = localization.exit,
                            typeStyle = Appearance.TypeStyle.TITLE,
                        )
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.align(Alignment.BottomCenter),
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun TopNavigationBarPreview() {
    MaterialThemePreview {
        TopNavigationBar(
            showBackNavigation = true,
            onBack = {
            },
        ) { }
    }
}
