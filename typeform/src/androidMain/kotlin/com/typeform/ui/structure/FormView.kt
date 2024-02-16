package com.typeform.ui.structure

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.typeform.models.Position
import com.typeform.models.Responses
import com.typeform.schema.Form
import com.typeform.ui.components.StyledTextView
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.NavigationAction
import com.typeform.ui.models.Settings
import com.typeform.ui.preview.preview

/**
 * The [FormView] presents a launching point for the typeform presentation.
 */
@Composable
fun FormView(
    form: Form,
    settings: Settings = Settings(),
    responses: Responses = mutableMapOf(),
    conclusion: (Conclusion) -> Unit,
    header: (@Composable () -> Unit)? = null,
) {
    val navController = rememberNavController()
    var showBackNavigation by remember { mutableStateOf(false) }
    var showConfirmCancel by remember { mutableStateOf(false) }

    val startPosition: Position? = remember {
        try {
            form.firstPosition(
                skipWelcomeScreen = settings.presentation.skipWelcomeScreen,
                responses = responses
            )
        } catch (_: Exception) {
            null
        }
    }

    val startDestination = remember {
        when (startPosition) {
            is Position.ScreenPosition -> {
                Pair(TypeformRoute.screen, startPosition.screen.id)
            }
            is Position.FieldPosition -> {
                Pair(TypeformRoute.field, startPosition.field.id)
            }
            else -> {
                Pair(TypeformRoute.rejected, "")
            }
        }
    }

    fun navigateUsing(navigationAction: NavigationAction) {
        when (navigationAction) {
            is NavigationAction.PositionAction -> {
                when (navigationAction.position) {
                    is Position.FieldPosition -> {
                        navController.navigate(TypeformRoute.makeField(navigationAction.position.field.id))
                    }
                    is Position.ScreenPosition -> {
                        navController.navigate(TypeformRoute.makeScreen(navigationAction.position.screen.id))
                    }
                }
            }
            is NavigationAction.ConclusionAction -> {
                conclusion(navigationAction.conclusion)
            }
            is NavigationAction.Back -> {
                navController.navigateUp()
            }
        }
    }

    Scaffold(
        topBar = {
            Surface(
                elevation = 5.dp,
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colors.background)
                        .fillMaxWidth(),
                ) {
                    Column(
                        modifier = Modifier.padding(settings.presentation.topBarPadding),
                    ) {
                        Row(
                            modifier = Modifier.heightIn(min = 48.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            if (showBackNavigation) {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowBack,
                                    contentDescription = null,
                                    modifier = Modifier.clickable {
                                        navigateUsing(NavigationAction.Back)
                                    },
                                )
                            }

                            Text(
                                text = " ",
                                modifier = Modifier.weight(1f),
                            )

                            Text(
                                text = settings.localization.cancel,
                                modifier = Modifier.clickable {
                                    if (responses.isEmpty()) {
                                        conclusion(Conclusion.Canceled)
                                    } else {
                                        showConfirmCancel = true
                                    }
                                },
                                color = MaterialTheme.colors.primary,
                                style = MaterialTheme.typography.subtitle1,
                            )
                        }
                    }

                    Divider(
                        modifier = Modifier.align(Alignment.BottomCenter),
                    )
                }
            }
        },
    ) { scaffoldPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination.first,
        ) {
            composable(
                route = TypeformRoute.screen,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                        nullable = false
                    },
                ),
            ) {
                showBackNavigation = false
                val id = (it.arguments?.getString("id") ?: startPosition?.associatedScreen()?.id) ?: form.firstScreen?.id
                if (id == null) {
                    RejectedView(
                        scaffoldPadding = scaffoldPadding,
                        settings = settings,
                        responses = responses
                    ) { rejection ->
                        conclusion(rejection)
                    }
                    return@composable
                }

                val screen = form.screenWithId(id)
                if (screen == null) {
                    RejectedView(
                        scaffoldPadding = scaffoldPadding,
                        settings = settings,
                        responses = responses,
                    ) { rejection ->
                        conclusion(rejection)
                    }
                    return@composable
                }

                ScreenView(
                    scaffoldPadding = scaffoldPadding,
                    form = form,
                    settings = settings,
                    screen = screen,
                    responses = responses,
                    actionHandler = { navigationAction ->
                        navigateUsing(navigationAction)
                    },
                )
            }

            composable(
                route = TypeformRoute.field,
                arguments = listOf(
                    navArgument("id") {
                        type = NavType.StringType
                        nullable = false
                    },
                ),
            ) {
                val fieldId = (it.arguments?.getString("id") ?: startPosition?.associatedField()?.id) ?: form.fields.firstOrNull()?.id
                if (fieldId == null) {
                    showBackNavigation = false
                    RejectedView(
                        scaffoldPadding = scaffoldPadding,
                        settings = settings,
                        responses = responses,
                    ) { rejection ->
                        conclusion(rejection)
                    }
                    return@composable
                }

                showBackNavigation = (startDestination.first != TypeformRoute.field || startDestination.second != fieldId)

                val field = form.fieldWithId(fieldId)
                if (field == null) {
                    RejectedView(
                        scaffoldPadding = scaffoldPadding,
                        settings = settings,
                        responses = responses,
                    ) { rejection ->
                        conclusion(rejection)
                    }
                    return@composable
                }

                val parent = form.parentForFieldWithId(fieldId)
                val group = parent?.associatedGroup()

                FieldView(
                    scaffoldPadding = scaffoldPadding,
                    form = form,
                    settings = settings,
                    field = field,
                    group = group,
                    responses = responses,
                    header = header,
                    actionHandler = { navigationAction ->
                        navigateUsing(navigationAction)
                    },
                )
            }

            composable(
                route = TypeformRoute.rejected,
            ) {
                showBackNavigation = false

                RejectedView(
                    scaffoldPadding = scaffoldPadding,
                    settings = settings,
                    responses = responses,
                ) { rejection ->
                    conclusion(rejection)
                }
            }
        }
    }

    if (showConfirmCancel) {
        AlertDialog(
            onDismissRequest = {
                showConfirmCancel = false
            },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 26.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        onClick = {
                            conclusion(Conclusion.Abandoned(responses))
                        },
                    ) {
                        StyledTextView(
                            text = settings.localization.abandonConfirmationAction,
                            textStyle = MaterialTheme.typography.body1,
                            color = Color.Red,
                        )
                    }

                    TextButton(
                        onClick = {
                            showConfirmCancel = false
                        },
                    ) {
                        StyledTextView(
                            text = settings.localization.cancel,
                            textStyle = MaterialTheme.typography.body1,
                            color = MaterialTheme.colors.primary,
                        )
                    }
                }
            },
            title = {
                StyledTextView(
                    text = settings.localization.abandonConfirmationTitle,
                    textStyle = MaterialTheme.typography.h5,
                )
            },
            text = {
                StyledTextView(
                    text = settings.localization.abandonConfirmationMessage,
                    textStyle = MaterialTheme.typography.body1,
                )
            },
            shape = RoundedCornerShape(CornerSize(16.dp)),
        )
    }
}

internal sealed class TypeformRoute {
    companion object {
        const val screen = "typeform_screen/{id}"
        const val field = "typeform_field/{id}"
        const val rejected = "typeform_rejected"

        fun makeScreen(id: String): String {
            return screen.replace("{id}", id)
        }

        fun makeField(fieldId: String): String {
            return field.replace("{id}", fieldId)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun FormViewPreview() {
    FormView(
        Form.preview,
        conclusion = { },
    )
}
