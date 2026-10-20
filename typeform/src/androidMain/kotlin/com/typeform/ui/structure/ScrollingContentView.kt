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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.typeform.ui.LocalColors
import com.typeform.ui.LocalPresentation
import com.typeform.ui.components.CallToActionContainerView
import com.typeform.ui.components.CallToActionContentView
import com.typeform.ui.preview.MaterialThemePreview
import kotlin.math.abs

/**
 * Internal layout that provides content scrolling and a 'Call-to-Action' style engagement button.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ScrollingContentView(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    header: (@Composable () -> Unit) = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    val presentation = LocalPresentation.current
    val colors = LocalColors.current
    val scrollState = rememberScrollState()
    val isKeyboardVisible by rememberUpdatedState(WindowInsets.isImeVisible)
    val animationDuration = 300
    val enterTransition = slideInVertically(tween(animationDuration)) { abs(it) } +
        fadeIn(tween(animationDuration))
    val exitTransition = slideOutVertically(tween(animationDuration)) { abs(it) } +
        fadeOut(tween(animationDuration))

    Surface(
        color = colors.surface,
    ) {
        Box(
            modifier = modifier,
        ) {
            Column {
                header()

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(scrollState)
                        .padding(presentation.contentPadding),
                    verticalArrangement = Arrangement.spacedBy(presentation.elementSpacing),
                ) {
                    content()

                    AnimatedVisibility(
                        visible = isKeyboardVisible,
                        enter = enterTransition,
                        exit = exitTransition,
                    ) {
                        CallToActionContentView(
                            title = title,
                            onClick = onClick,
                            enabled = enabled,
                        )
                    }
                }

                AnimatedVisibility(
                    visible = !isKeyboardVisible,
                    enter = enterTransition,
                    exit = exitTransition,
                ) {
                    CallToActionContainerView {
                        CallToActionContentView(
                            title = title,
                            onClick = onClick,
                            enabled = enabled,
                        )
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
            title = "Preview",
            onClick = {},
            header = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Gray)
                        .height(50.dp),
                )
            },
        ) {
            Box(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .background(Color.Blue),
            ) {
            }

            Box(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .background(Color.Yellow),
            ) {
            }

            Box(
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
                    .background(Color.Red),
            ) {
            }
        }
    }
}
