package com.typeform.schema.structure

import kotlinx.serialization.Serializable

@Serializable
data class Settings(
    val meta: Meta = Meta(),
    val is_trial: Boolean = false,
    val language: String = "en",
    val translation_languages: List<String>? = null,
    val is_public: Boolean = true,
    val capabilities: Capabilities? = null,
    val progress_bar: String = "",
    val hide_navigation: Boolean = false,
    val show_progress_bar: Boolean = false,
    val are_uploads_public: Boolean = false,
    val show_cookie_consent: Boolean = false,
    val pro_subdomain_enabled: Boolean = false,
    val show_time_to_complete: Boolean = false,
    val show_typeform_branding: Boolean = false,
    val show_number_of_submissions: Boolean = false,
) {
    @Serializable
    data class Meta(
        val allow_indexing: Boolean = false,
    )

    @Serializable
    data class Capabilities(
        val e2e_encryption: EndToEndEncryption? = null,
    )

    @Serializable
    data class EndToEndEncryption(
        val enabled: Boolean = false,
        val modifiable: Boolean = false,
    )
}
