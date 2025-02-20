package com.typeform.example.ui.content

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.SingletonImageLoader
import coil3.compose.LocalPlatformContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.typeform.example.model.ExampleUploadHelper
import com.typeform.example.ui.theme.ExampleTheme
import com.typeform.schema.Form
import com.typeform.ui.models.Conclusion
import com.typeform.ui.models.Settings
import com.typeform.ui.structure.FormView

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Navigation(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    var form: Form? by remember { mutableStateOf(null) }
    var settings: Settings by remember { mutableStateOf(Settings()) }
    var conclusion: Conclusion? by remember { mutableStateOf(null) }
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    NavHost(
        navController = navController,
        startDestination = "content",
        modifier = modifier,
    ) {
        composable(
            route = "content",
        ) {
            ContentView(
                conclusion = conclusion,
                displayFormHandler = { inForm, inSettings ->
                    form = inForm
                    settings = inSettings
                    navController.navigate("form")
                }
            )
        }

        composable(
            route = "form",
        ) {
            form?.let {
                FormView(
                    form = it,
                    settings = settings,
                    imageLoader = SingletonImageLoader.get(LocalPlatformContext.current),
                    uploadHelper = ExampleUploadHelper(
                        cameraPermissionState = cameraPermissionState,
                    ),
                    conclusion = {
                        conclusion = it
                        navController.navigate("content")
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NavigationPreview() {
    ExampleTheme {
        Navigation()
    }
}
