package com.typeform

import com.typeform.schema.Form
import com.typeform.schema.translation.TranslatedForm
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

class TypeformDownloader : Downloader {

    private val client = HttpClient()

    override suspend fun downloadForm(id: String): Result<Form> {
        return try {
            val url = "https://api.typeform.com/forms/$id"
            val response = client.get(urlString = url)
            val body = response.bodyAsText()
            val form: Form = Typeform.json.decodeFromString(body)
            Result.success(form)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }

    override suspend fun downloadTranslation(
        language: String,
        formId: String,
    ): Result<TranslatedForm> {
        return try {
            val url = "https://api.typeform.com/forms/$formId/translation/$language"
            val response = client.get(urlString = url)
            val body = response.bodyAsText()
            val translation: TranslatedForm = Typeform.json.decodeFromString(body)
            Result.success(translation)
        } catch (exception: Exception) {
            Result.failure(exception)
        }
    }
}
