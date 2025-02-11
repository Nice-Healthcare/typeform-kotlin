package com.typeform.schema

interface Screen {
    val id: String
    val title: String
    val attachment: Attachment?
    val properties: ScreenProperties
}
