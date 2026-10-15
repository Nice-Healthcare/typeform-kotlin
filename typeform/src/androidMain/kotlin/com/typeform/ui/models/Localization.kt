package com.typeform.ui.models

/**
 * String values used in the presentation of the form.
 */
data class Localization(
    val next: String = "Next",
    val cancel: String = "Cancel",
    val finish: String = "Finish",
    val yes: String = "Yes",
    val no: String = "No",
    val exit: String = "Exit",
    val abandonConfirmationTitle: String = "Abandon Form?",
    val abandonConfirmationMessage: String = "Are you sure you want to abandon the form?",
    val abandonConfirmationAction: String = "Abandon",
    val emptyChoice: String = "Select an Option",
    val nullDate: String = "I'm not sure…",
    val uploadAction: String = "Select File",
    val uploadCamera: String = "Camera",
    val uploadPhotoLibrary: String = "Photo Library",
    val uploadDocument: String = "Documents",
)
