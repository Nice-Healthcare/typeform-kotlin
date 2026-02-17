package com.typeform.ui.preview

import com.typeform.schema.questions.DateStamp
import com.typeform.schema.questions.Dropdown
import com.typeform.schema.questions.MultipleChoice
import com.typeform.schema.structure.Choice
import com.typeform.schema.structure.Field
import com.typeform.schema.structure.FieldProperties
import com.typeform.schema.structure.FieldType
import com.typeform.schema.structure.Statement
import com.typeform.schema.structure.Validations

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

val Field.Companion.previewMatrix1: Field
    get() = Field(
        id = "Fg0BEvAfhgJ5",
        ref = "707fbfbd-9e4e-46ac-b767-7a982c2c996e",
        type = FieldType.MULTIPLE_CHOICE,
        title = "Pain from a broken bone",
        properties = FieldProperties.MultipleChoiceProperties(
            properties = MultipleChoice(
                choices = listOf(
                    Choice(
                        id = "LcSOZ2D6GN9V",
                        ref = "d0962818-9402-4d40-91be-25daa203cfa7",
                        label = "Yes",
                    ),
                    Choice(
                        id = "YZ10oAwDUq6K",
                        ref = "22279395-5972-46d7-a5b7-2f2b9e4a12e6",
                        label = "No",
                    ),
                ),
                randomize = false,
                allow_multiple_selection = false,
                allow_other_choice = false,
                vertical_alignment = true,
                description = null,
            ),
        ),
        validations = Validations(true),
        attachment = null,
    )

val Field.Companion.previewMatrix2: Field
    get() = Field(
        id = "XNP75E1RPXKD",
        ref = "d7799e3f-8647-44a2-8a8b-5039c1ac253c",
        type = FieldType.MULTIPLE_CHOICE,
        title = "Concussion",
        properties = FieldProperties.MultipleChoiceProperties(
            properties = MultipleChoice(
                choices = listOf(
                    Choice(
                        id = "fluKtoKs3UbG",
                        ref = "9d01e6d3-8040-479c-95fb-7d1fceca6f8f",
                        label = "Yes",
                    ),
                    Choice(
                        id = "XIMW5ga0dENm",
                        ref = "0617e0e8-a49e-40d2-8eb5-4b3dae5f84b3",
                        label = "No",
                    ),
                ),
                randomize = false,
                allow_multiple_selection = false,
                allow_other_choice = false,
                vertical_alignment = true,
                description = null,
            ),
        ),
        validations = Validations(true),
        attachment = null,
    )

val Field.Companion.previewMatrix3: Field
    get() = Field(
        id = "l81tBCQxJesj",
        ref = "67396128-6aef-430f-afc7-6f8f645b7628",
        type = FieldType.MULTIPLE_CHOICE,
        title = "Pain from a motor vehicle accident",
        properties = FieldProperties.MultipleChoiceProperties(
            properties = MultipleChoice(
                choices = listOf(
                    Choice(
                        id = "3HUgFvzJRdq4",
                        ref = "d7fb394d-05c2-4857-962e-165fb4a419b8",
                        label = "Yes",
                    ),
                    Choice(
                        id = "VnAGMTm3Drpd",
                        ref = "18888961-b879-4fbc-8404-095f913d2b45",
                        label = "No",
                    ),
                ),
                randomize = false,
                allow_multiple_selection = false,
                allow_other_choice = false,
                vertical_alignment = true,
                description = null,
            ),
        ),
        validations = Validations(true),
        attachment = null,
    )
