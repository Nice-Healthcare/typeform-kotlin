package com.typeform.schema.structure

interface Screen {
    val id: String
    val title: String
    val attachment: Attachment?
    val properties: ScreenProperties
}
