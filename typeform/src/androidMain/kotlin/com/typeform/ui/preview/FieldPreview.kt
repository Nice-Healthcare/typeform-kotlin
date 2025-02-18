package com.typeform.ui.preview

import com.typeform.schema.Choice
import com.typeform.schema.DateStamp
import com.typeform.schema.Dropdown
import com.typeform.schema.Field
import com.typeform.schema.FieldProperties
import com.typeform.schema.FieldType
import com.typeform.schema.Statement
import com.typeform.schema.Validations

val Field.Companion.previewDate: Field
    get() = Field(
        id = "0B169EA1-E110-4E76-9D35-EDB9C6E754B5",
        ref = "317343B4-2C34-40A0-B5F2-9D6DF8CB10B7",
        type = FieldType.DATE,
        title = "What is your birthday?",
        properties = FieldProperties.DateStampProperties(
            properties = DateStamp(
                separator = "/",
                structure = "MMDDYYYY",
                description = null,
            ),
        ),
        validations = Validations(
            required = true,
        ),
        attachment = null,
    )

val Field.Companion.previewDropdown: Field
    get() = Field(
        id = "15E61661-226C-4B2E-94EE-C478347BB660",
        ref = "4E8757FB-E2C8-4A95-8FE2-0025AF6A2154",
        type = FieldType.DROPDOWN,
        title = "What is your preferred temperature?",
        properties = FieldProperties.DropdownProperties(
            properties = Dropdown(
                choices = listOf(
                    Choice.previewHot,
                    Choice.previewCold,
                ),
                description = null,
                randomize = false,
                alphabetical_order = false,
            ),
        ),
        validations = Validations(
            required = true,
        ),
        attachment = null,
    )

val Field.Companion.previewStatement: Field
    get() = Field(
        id = "A37F8750-3E16-4626-A09E-29E51162F625",
        ref = "5F3AFEF7-55F1-4500-B13A-99606DA24780",
        type = FieldType.STATEMENT,
        title = "Please read carefully.",
        properties = FieldProperties.StatementProperties(
            properties = Statement(
                hide_marks = false,
                button_text = "Agree",
                description = "You must agree to this statement in order to continue.",
            ),
        ),
        validations = null,
        attachment = null,
    )
