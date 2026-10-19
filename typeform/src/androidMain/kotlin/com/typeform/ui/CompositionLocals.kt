package com.typeform.ui

import androidx.compose.runtime.compositionLocalOf
import coil3.ImageLoader
import com.typeform.ui.models.ColorConfiguration
import com.typeform.ui.models.LocalizationConfiguration
import com.typeform.ui.models.PresentationConfiguration
import com.typeform.ui.models.TypographyConfiguration
import com.typeform.ui.models.UploadHelper

/**
 * Composition Local providing access to a [ImageLoader] throughout the hierarchy.
 */
internal val LocalImageLoader = compositionLocalOf<ImageLoader?> { null }

/**
 * Composition Local providing access to a [UploadHelper] throughout the hierarchy.
 */
internal val LocalUploadHelper = compositionLocalOf<UploadHelper?> { null }

internal val LocalLocalization = compositionLocalOf { LocalizationConfiguration() }
internal val LocalPresentation = compositionLocalOf { PresentationConfiguration() }
internal val LocalTypography = compositionLocalOf { TypographyConfiguration() }
internal val LocalColors = compositionLocalOf { ColorConfiguration.lightExample }
