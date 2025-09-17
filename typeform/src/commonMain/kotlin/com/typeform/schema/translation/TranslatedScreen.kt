package com.typeform.schema.translation

import com.typeform.schema.structure.EndingScreen
import com.typeform.schema.structure.WelcomeScreen
import kotlinx.serialization.Serializable

@Serializable
data class TranslatedScreen(
    val id: String,
    val title: String,
    val properties: TranslatedProperties,
)

fun WelcomeScreen.merging(translatedScreen: TranslatedScreen): WelcomeScreen {
    return copy(
        title = translatedScreen.title,
        properties = properties.merging(translatedScreen.properties),
    )
}

fun List<WelcomeScreen>.mergingWelcomeTranslations(translatedScreens: List<TranslatedScreen>?): List<WelcomeScreen> {
    if (translatedScreens == null) {
        return this
    }

    val screens = this.toMutableList()
    for (screen in translatedScreens) {
        val index = screens.indexOfFirst { it.id == screen.id }
        if (index != -1) {
            screens[index] = screens[index].merging(screen)
        }
    }

    return screens
}

fun EndingScreen.merging(translatedScreen: TranslatedScreen): EndingScreen {
    return copy(
        title = translatedScreen.title,
        properties = properties.merging(translatedScreen.properties),
    )
}

fun List<EndingScreen>.mergingEndingTranslations(translatedScreens: List<TranslatedScreen>?): List<EndingScreen> {
    if (translatedScreens == null) {
        return this
    }

    val screens = this.toMutableList()
    for (screen in translatedScreens) {
        val index = screens.indexOfFirst { it.id == screen.id }
        if (index != -1) {
            screens[index] = screens[index].merging(screen)
        }
    }

    return screens
}
