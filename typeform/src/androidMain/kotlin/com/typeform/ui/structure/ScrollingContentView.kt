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
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.typeform.ui.models.Settings
import com.typeform.ui.preview.ThemePreview
import kotlin.math.abs

/**
 * Internal layout that provides content scrolling and a 'Call-to-Action' style engagement button.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun ScrollingContentView(
    scaffoldPadding: PaddingValues,
    settings: Settings,
    title: String = "",
    onClick: () -> Unit,
    enabled: Boolean = true,
    header: (@Composable () -> Unit)? = null,
    content: @Composable BoxScope.() -> Unit,
) {
    val scrollState = rememberScrollState()
    val isKeyboardVisible by rememberUpdatedState(if (LocalInspectionMode.current) false else WindowInsets.isImeVisible)
    val animationDuration = 300

    Column(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .padding(scaffoldPadding),
        verticalArrangement = Arrangement.spacedBy(0.dp),
    ) {
        header?.let {
            it()
        }

        Box(
            modifier = Modifier.weight(1f),
        ) {
            Box(
                modifier = Modifier.verticalScroll(scrollState),
                content = content,
            )
        }

        AnimatedVisibility(
            visible = !isKeyboardVisible,
            enter = slideInVertically(tween(animationDuration)) { abs(it) } + fadeIn(tween(animationDuration)),
            exit = slideOutVertically(tween(animationDuration)) { abs(it) } + fadeOut(tween(animationDuration)),
        ) {
            Box(
                modifier = Modifier.background(settings.callToAction.backgroundColor),
            ) {
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    color = settings.callToAction.dividerColor,
                    thickness = 1.dp,
                )

                Column(
                    modifier = Modifier
                        .padding(settings.callToAction.containerPadding),
                ) {
                    Button(
                        onClick = onClick,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = enabled,
                        elevation = null,
                        colors = settings.callToAction.colors,
                        contentPadding = settings.callToAction.contentPadding,
                    ) {
                        Text(
                            text = title,
                            color = settings.callToAction.colors.contentColor(enabled = enabled).value,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ScrollingContentViewPreview(settings: Settings = Settings()) {
    ThemePreview {
        ScrollingContentView(
            scaffoldPadding = PaddingValues(0.dp),
            settings = settings,
            title = "Preview",
            onClick = {},
            header = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Cyan)
                        .height(50.dp),
                )
            },
        ) {
            Column(
                modifier = Modifier.padding(settings.presentation.contentPadding),
            ) {
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
