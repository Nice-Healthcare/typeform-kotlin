package com.typeform.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import com.typeform.example.ui.content.ContentView
import com.typeform.example.ui.state.BooleanSetting
import com.typeform.example.ui.theme.ExampleTheme
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val coroutineScope = rememberCoroutineScope()
            val dynamicTheme = BooleanSetting(
                context = applicationContext,
                preferenceKey = "dynamic_color",
            )

            setSingletonImageLoaderFactory { platformContext ->
                ImageLoader.Builder(platformContext)
                    .components {
                        add(
                            KtorNetworkFetcherFactory(
                                httpClient = {
                                    HttpClient()
                                }
                            )
                        )
                    }
                    .build()
            }

            val dynamicColor = dynamicTheme
                .isEnabled(false)
                .collectAsState(false)

            ExampleTheme(
                dynamicColor = dynamicColor.value,
            ) {
                ContentView(
                    modifier = Modifier
                        .imePadding()
                        .systemBarsPadding(),
                    dynamicColors = dynamicColor.value,
                    onDynamicColorsChange = {
                        coroutineScope.launch {
                            dynamicTheme.setEnabled(it)
                        }
                    },
                )
            }
        }
    }
}
