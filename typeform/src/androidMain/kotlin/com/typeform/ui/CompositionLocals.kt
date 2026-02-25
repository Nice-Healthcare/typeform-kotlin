package com.typeform.ui

import androidx.compose.runtime.compositionLocalOf
import coil3.ImageLoader
import com.typeform.ui.models.Settings
import com.typeform.ui.models.UploadHelper

/**
 * Composition Local providing access to UI [Settings] throughout the hierarchy.
 */
internal val LocalSettings = compositionLocalOf { Settings() }

/**
 * Composition Local providing access to a [ImageLoader] throughout the hierarchy.
 */
internal val LocalImageLoader = compositionLocalOf<ImageLoader?> { null }

/**
 * Composition Local providing access to a [UploadHelper] throughout the hierarchy.
 */
internal val LocalUploadHelper = compositionLocalOf<UploadHelper?> { null }
