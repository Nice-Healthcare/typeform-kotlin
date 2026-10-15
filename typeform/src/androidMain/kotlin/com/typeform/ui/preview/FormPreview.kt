package com.typeform.ui.preview

import com.typeform.schema.structure.Attachment
import com.typeform.schema.structure.AttachmentProperties
import com.typeform.schema.structure.AttachmentType
import com.typeform.schema.structure.Field
import com.typeform.schema.structure.Form
import com.typeform.schema.structure.FormType
import com.typeform.schema.structure.Links
import com.typeform.schema.structure.ScreenProperties
import com.typeform.schema.structure.Settings
import com.typeform.schema.structure.Theme
import com.typeform.schema.structure.WelcomeScreen
import com.typeform.schema.structure.Workspace

val Form.Companion.preview: Form
    get() = Form(
        id = "123abc",
        type = FormType.QUIZ,
        logic = emptyList(),
        theme = Theme(
            href = "https://www.typeform.com",
        ),
        title = "",
        links = Links(
            display = "https://www.typeform.com",
        ),
        fields = listOf(
            Field.previewStatement,
            Field.previewDropdown,
            Field.previewDate,
        ),
        hidden = emptyList(),
        settings = Settings(
            meta = Settings.Meta(
                allow_indexing = false,
            ),
            is_trial = false,
            language = "en",
            translation_languages = null,
            is_public = true,
            capabilities = Settings.Capabilities(
                e2e_encryption = Settings.EndToEndEncryption(
                    enabled = false,
                    modifiable = false,
                ),
            ),
            progress_bar = "",
            hide_navigation = false,
            show_progress_bar = false,
            are_uploads_public = false,
            show_cookie_consent = false,
            pro_subdomain_enabled = false,
            show_time_to_complete = false,
            show_typeform_branding = false,
            show_number_of_submissions = false,
        ),
        workspace = Workspace(
            href = "https://www.typeform.com",
        ),
        welcomeScreens = listOf(
            WelcomeScreen(
                id = "welcome1",
                ref = "welcome1",
                title = "Example Welcome Screen",
                attachment = Attachment(
                    href = "https://images.typeform.com/images/nicGuchsjTJG",
                    type = AttachmentType.IMAGE,
                    properties = AttachmentProperties(
                        description = "",
                    ),
                ),
                properties = ScreenProperties(
                    button_mode = null,
                    button_text = "Continue",
                    share_icons = null,
                    show_button = true,
                ),
            ),
        ),
        endingScreens = emptyList(),
    )
