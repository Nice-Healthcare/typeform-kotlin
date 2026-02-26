package com.typeform.ui.structure

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.ui.LocalPresentation
import com.typeform.ui.preview.MaterialThemePreview
import kotlin.math.abs

/**
 * Internal layout that provides content scrolling and a 'Call-to-Action' style engagement button.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ScrollingContentView(
    scaffoldPadding: PaddingValues,
    title: String = "",
    onClick: () -> Unit,
    enabled: Boolean = true,
    header: (@Composable () -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val scrollState = rememberScrollState()
    val isKeyboardVisible by rememberUpdatedState(if (LocalInspectionMode.current) false else WindowInsets.isImeVisible)
    val animationDuration = 300

    Surface {
        Column(
            modifier = Modifier
                .padding(scaffoldPadding),
            verticalArrangement = Arrangement.spacedBy(0.dp),
        ) {
            header?.let {
                it()
            }

            Box(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .weight(1f)
                    .fillMaxWidth(),
                content = content,
            )

            AnimatedVisibility(
                visible = !isKeyboardVisible,
                enter = slideInVertically(tween(animationDuration)) { abs(it) } + fadeIn(tween(animationDuration)),
                exit = slideOutVertically(tween(animationDuration)) { abs(it) } + fadeOut(tween(animationDuration)),
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                ) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                    )

                    Box(
                        modifier = Modifier
                            .padding(LocalPresentation.current.containerPadding),
                    ) {
                        Button(
                            onClick = onClick,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = enabled,
                        ) {
                            Text(
                                text = title,
                            )
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun ScrollingContentViewPreview() {
    MaterialThemePreview {
        ScrollingContentView(
            scaffoldPadding = PaddingValues(0.dp),
            title = "Preview",
            onClick = {},
            header = {
                Surface(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                    )
                }
            },
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .width(50.dp)
                        .background(Color.Blue),
                ) {
                }

                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .width(50.dp)
                        .background(Color.Yellow),
                ) {
                }

                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .width(50.dp)
                        .background(Color.Red),
                ) {
                }
            }
        }
    }
}
