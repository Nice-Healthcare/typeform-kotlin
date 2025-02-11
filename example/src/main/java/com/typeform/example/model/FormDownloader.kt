package com.typeform.example.model

import com.typeform.Typeform
import com.typeform.schema.Form
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

class FormDownloader {

    sealed class FormException : Exception() {
        data object Download : FormException()
        data object Decoding : FormException()
    }

    private val client = HttpClient()

    @Throws(FormException::class)
    suspend fun getForm(formId: String): Form {
        val url = "https://api.typeform.com/forms/$formId"
        val response = client.get(urlString = url)
        if (response.status != HttpStatusCode.OK) {
            throw FormException.Download
        }

        val body = response.bodyAsText()

        try {
            return Typeform.json.decodeFromString(body)
        } catch (_: Exception) {
            throw FormException.Decoding
        }
    }
}
