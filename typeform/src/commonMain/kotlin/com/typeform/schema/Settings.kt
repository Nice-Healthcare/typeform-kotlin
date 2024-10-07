package com.typeform.schema

data class Settings(
    val meta: Meta,
    val is_trial: Boolean,
    val language: String,
    val is_public: Boolean,
    val capabilities: Capabilities?,
    val progress_bar: String,
    val hide_navigation: Boolean,
    val show_progress_bar: Boolean,
    val are_uploads_public: Boolean,
    val show_cookie_consent: Boolean,
    val pro_subdomain_enabled: Boolean,
    val show_time_to_complete: Boolean,
    val show_typeform_branding: Boolean,
    val show_number_of_submissions: Boolean,
) {
    data class Meta(
        val allow_indexing: Boolean,
    )

    data class Capabilities(
        val e2e_encryption: EndToEndEncryption?,
    )

    data class EndToEndEncryption(
        val enabled: Boolean,
        val modifiable: Boolean,
    )
}
