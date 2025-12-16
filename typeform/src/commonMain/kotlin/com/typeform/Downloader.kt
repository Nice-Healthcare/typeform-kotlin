package com.typeform

import com.typeform.schema.structure.Form
import com.typeform.schema.translation.TranslatedForm

interface Downloader {
    suspend fun downloadForm(id: String): Result<Form>

    suspend fun downloadTranslation(
        language: String,
        formId: String,
    ): Result<TranslatedForm>
}
