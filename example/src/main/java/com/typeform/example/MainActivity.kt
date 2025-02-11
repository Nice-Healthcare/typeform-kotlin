package com.typeform.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import com.typeform.example.ui.content.Navigation
import com.typeform.example.ui.theme.ExampleTheme
import io.ktor.client.HttpClient

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
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

            ExampleTheme {
                Navigation(
                    modifier = Modifier
                        .imePadding()
                        .systemBarsPadding()
                )
            }
        }
    }
}
