package com.typeform.ui.structure

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.ImageLoader
import com.typeform.models.Position
import com.typeform.models.Responses
import com.typeform.schema.structure.Form
import com.typeform.ui.LocalImageLoader
import com.typeform.ui.LocalLocalization
import com.typeform.ui.LocalLogic
import com.typeform.ui.LocalPresentation
import com.typeform.ui.LocalTextStyles
import com.typeform.ui.LocalUploadHelper
import com.typeform.ui.components.TextView
import com.typeform.ui.components.TopNavigationBar
import com.typeform.ui.models.Appearance
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.NavigationAction
import com.typeform.ui.models.Settings
import com.typeform.ui.models.Script
import com.typeform.ui.models.TypeformRoute
import com.typeform.ui.models.UploadHelper
import com.typeform.ui.preview.MaterialThemePreview
import com.typeform.ui.preview.preview

/**
 * The [FormView] presents a launching point for the typeform presentation.
 */
@Composable
fun FormView(
    form: Form,
    appearance: Appearance = Appearance(),
    responses: Responses = mapOf(),
    imageLoader: ImageLoader? = null,
    uploadHelper: UploadHelper? = null,
    header: (@Composable () -> Unit)? = null,
    conclusion: (Conclusion) -> Unit,
) {
    val navController = rememberNavController()
    var showBackNavigation by remember { mutableStateOf(false) }
    var showConfirmCancel by remember { mutableStateOf(false) }
    var collectedResponses: Responses by remember { mutableStateOf(responses) }

    val startPosition: Position? = remember {
        try {
            form.firstPosition(
                skipWelcomeScreen = appearance.additionalLogic.skipWelcomeScreen,
                responses = responses,
            )
        } catch (_: Exception) {
            null
        }
    }

    // Pair<TypeformRoute, String(Destination)>
    val startDestination = remember {
        when (startPosition) {
            is Position.ScreenPosition -> {
                Pair(TypeformRoute.SCREEN, TypeformRoute.makeScreen(startPosition.screen.id))
            }
            is Position.FieldPosition -> {
                Pair(TypeformRoute.FIELD, TypeformRoute.makeField(startPosition.field.id))
            }
            else -> {
                Pair(TypeformRoute.REJECTED, TypeformRoute.REJECTED)
            }
        }
    }

    fun navigateUsing(navigationAction: NavigationAction) {
        when (navigationAction) {
            is NavigationAction.PositionAction -> {
                collectedResponses = navigationAction.responses.toMutableMap()

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

    CompositionLocalProvider(
        LocalTextStyles provides appearance.textStyles,
        LocalPresentation provides appearance.presentation,
        LocalLocalization provides appearance.localization,
        LocalLogic provides appearance.additionalLogic,
        LocalUploadHelper provides uploadHelper,
        LocalImageLoader provides imageLoader,
    ) {
        Scaffold(
            topBar = {
                TopNavigationBar(
                    showBackNavigation = showBackNavigation,
                    onBack = {
                        navigateUsing(NavigationAction.Back)
                    },
                ) {
                    if (collectedResponses.isEmpty()) {
                        conclusion(Conclusion.Canceled)
                    } else {
                        showConfirmCancel = true
                    }
                }
            },
        ) { scaffoldPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination.second,
            ) {
                composable(
                    route = TypeformRoute.SCREEN,
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
                            modifier = Modifier.padding(scaffoldPadding),
                            responses = collectedResponses,
                        ) { rejection ->
                            conclusion(rejection)
                        }
                        return@composable
                    }

                    val screen = form.screenWithId(id)
                    if (screen == null) {
                        RejectedView(
                            modifier = Modifier.padding(scaffoldPadding),
                            responses = collectedResponses,
                        ) { rejection ->
                            conclusion(rejection)
                        }
                        return@composable
                    }

                    ScreenView(
                        modifier = Modifier.padding(scaffoldPadding),
                        form = form,
                        screen = screen,
                        responses = collectedResponses,
                        actionHandler = { navigationAction ->
                            navigateUsing(navigationAction)
                        },
                    )
                }

                composable(
                    route = TypeformRoute.FIELD,
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
                            modifier = Modifier.padding(scaffoldPadding),
                            responses = collectedResponses,
                        ) { rejection ->
                            conclusion(rejection)
                        }
                        return@composable
                    }

                    showBackNavigation = (startDestination.first != TypeformRoute.FIELD || !startDestination.second.contains(fieldId))

                    val field = form.fieldWithId(fieldId)
                    if (field == null) {
                        RejectedView(
                            modifier = Modifier.padding(scaffoldPadding),
                            responses = collectedResponses,
                        ) { rejection ->
                            conclusion(rejection)
                        }
                        return@composable
                    }

                    val parent = form.parentForFieldWithId(fieldId)
                    val group = parent?.associatedGroup()

                    FieldView(
                        modifier = Modifier.padding(scaffoldPadding),
                        form = form,
                        field = field,
                        group = group,
                        responses = collectedResponses,
                        header = header,
                        actionHandler = { navigationAction ->
                            navigateUsing(navigationAction)
                        },
                    )
                }

                composable(
                    route = TypeformRoute.REJECTED,
                ) {
                    showBackNavigation = false

                    RejectedView(
                        modifier = Modifier.padding(scaffoldPadding),
                        responses = collectedResponses,
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
                confirmButton = {
                    TextButton(
                        onClick = {
                            conclusion(Conclusion.Abandoned(collectedResponses))
                        },
                    ) {
                        TextView(
                            text = appearance.localization.abandonConfirmationAction,
                            script = Script.TITLE,
                        )
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showConfirmCancel = false
                        },
                    ) {
                        TextView(
                            text = appearance.localization.cancel,
                            script = Script.TITLE,
                        )
                    }
                },
                title = {
                    TextView(
                        text = appearance.localization.abandonConfirmationTitle,
                        script = Script.HEADLINE,
                    )
                },
                text = {
                    TextView(
                        text = appearance.localization.abandonConfirmationMessage,
                        script = Script.BODY,
                    )
                },
            )
        }
    }
}

@Deprecated(message = "Use [appearance] constructor.")
@Composable
fun FormView(
    form: Form,
    settings: Settings,
    responses: Responses = mapOf(),
    imageLoader: ImageLoader? = null,
    uploadHelper: UploadHelper? = null,
    conclusion: (Conclusion) -> Unit,
    header: (@Composable () -> Unit)? = null,
) {
    FormView(
        form = form,
        appearance = settings,
        responses = responses,
        imageLoader = imageLoader,
        uploadHelper = uploadHelper,
        header = header,
        conclusion = conclusion,
    )
}

@PreviewLightDark
@Composable
private fun FormViewPreview() {
    MaterialThemePreview {
        FormView(
            form = Form.preview,
        ) {
        }
    }
}
