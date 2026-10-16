package com.typeform.ui.preview

import com.typeform.schema.structure.Attachment
import com.typeform.schema.structure.AttachmentProperties
import com.typeform.schema.structure.AttachmentType
import com.typeform.schema.structure.Field
import com.typeform.schema.structure.Form
import com.typeform.schema.structure.Links
import com.typeform.schema.structure.ScreenProperties
import com.typeform.schema.structure.Theme
import com.typeform.schema.structure.WelcomeScreen
import com.typeform.schema.structure.Workspace

val Form.Companion.preview: Form
    get() = Form(
        id = "123abc",
        theme = Theme(
            href = "https://www.typeform.com",
        ),
        links = Links(
            display = "https://www.typeform.com",
        ),
        fields = listOf(
            Field.previewStatement,
            Field.previewDropdown,
            Field.previewDate,
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
