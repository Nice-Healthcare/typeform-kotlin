package com.typeform.ui

import androidx.compose.runtime.compositionLocalOf
import coil3.ImageLoader
import com.typeform.ui.models.Appearance
import com.typeform.ui.models.Localization
import com.typeform.ui.models.Presentation
import com.typeform.ui.models.UploadHelper

internal val LocalAppearance = compositionLocalOf { Appearance() }
internal val LocalPresentation = compositionLocalOf { Presentation() }
internal val LocalLocalization = compositionLocalOf { Localization() }

/**
 * Composition Local providing access to a [ImageLoader] throughout the hierarchy.
 */
internal val LocalImageLoader = compositionLocalOf<ImageLoader?> { null }

/**
 * Composition Local providing access to a [UploadHelper] throughout the hierarchy.
 */
internal val LocalUploadHelper = compositionLocalOf<UploadHelper?> { null }
