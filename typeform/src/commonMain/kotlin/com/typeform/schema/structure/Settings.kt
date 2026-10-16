package com.typeform.schema.structure

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val meta: Meta = Meta(),
    @SerialName("is_trial")
    val isTrial: Boolean = false,
    val language: String = "en",
    @SerialName("translation_languages")
    val translationLanguages: List<String>? = null,
    @SerialName("is_public")
    val isPublic: Boolean = false,
    val capabilities: Capabilities? = null,
    @SerialName("progress_bar")
    val progressBar: String = "",
    @SerialName("hide_navigation")
    val hideNavigation: Boolean = false,
    @SerialName("show_progress_bar")
    val showProgressBar: Boolean = false,
    @SerialName("are_uploads_public")
    val areUploadsPublic: Boolean = false,
    @SerialName("show_cookie_consent")
    val showCookieConsent: Boolean = false,
    @SerialName("pro_subdomain_enabled")
    val proSubdomainEnabled: Boolean = false,
    @SerialName("show_time_to_complete")
    val showTimeToComplete: Boolean = false,
    @SerialName("show_typeform_branding")
    val showTypeformBranding: Boolean = false,
    @SerialName("show_number_of_submissions")
    val showNumberOfSubmissions: Boolean = false,
) {
    @Deprecated(message = "", replaceWith = ReplaceWith("isTrial"))
    val is_trial: Boolean
        get() = isTrial

    @Deprecated(message = "", replaceWith = ReplaceWith("translationLanguages"))
    val translation_languages: List<String>?
        get() = translationLanguages

    @Deprecated(message = "", replaceWith = ReplaceWith("isPublic"))
    val is_public: Boolean
        get() = isPublic

    @Deprecated(message = "", replaceWith = ReplaceWith("progressBar"))
    val progress_bar: String
        get() = progressBar

    @Deprecated(message = "", replaceWith = ReplaceWith("hideNavigation"))
    val hide_navigation: Boolean
        get() = hideNavigation

    @Deprecated(message = "", replaceWith = ReplaceWith("showProgressBar"))
    val show_progress_bar: Boolean
        get() = showProgressBar

    @Deprecated(message = "", replaceWith = ReplaceWith("areUploadsPublic"))
    val are_uploads_public: Boolean
        get() = areUploadsPublic

    @Deprecated(message = "", replaceWith = ReplaceWith("showCookieConsent"))
    val show_cookie_consent: Boolean
        get() = showCookieConsent

    @Deprecated(message = "", replaceWith = ReplaceWith("proSubdomainEnabled"))
    val pro_subdomain_enabled: Boolean
        get() = proSubdomainEnabled

    @Deprecated(message = "", replaceWith = ReplaceWith("showTimeToComplete"))
    val show_time_to_complete: Boolean
        get() = showTimeToComplete

    @Deprecated(message = "", replaceWith = ReplaceWith("showTypeformBranding"))
    val show_typeform_branding: Boolean
        get() = showTypeformBranding

    @Deprecated(message = "", replaceWith = ReplaceWith("showNumberOfSubmissions"))
    val show_number_of_submissions: Boolean
        get() = showNumberOfSubmissions

    @Serializable
    data class Meta(
        @SerialName("allow_indexing")
        val allowIndexing: Boolean = false,
    ) {
        @Deprecated(message = "", replaceWith = ReplaceWith("allowIndexing"))
        val allow_indexing: Boolean
            get() = allowIndexing
    }

    @Serializable
    data class Capabilities(
        @SerialName("e2e_encryption")
        val endToEndEncryption: EndToEndEncryption?,
    ) {
        @Deprecated(message = "", replaceWith = ReplaceWith("endToEndEncryption"))
        val e2e_encryption: EndToEndEncryption?
            get() = endToEndEncryption
    }

    @Serializable
    data class EndToEndEncryption(
        val enabled: Boolean,
        val modifiable: Boolean,
    )
}
