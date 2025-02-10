package com.typeform.example.model

import com.typeform.Typeform
import com.typeform.schema.Form
import okhttp3.OkHttpClient
import okhttp3.Request

class FormHandler {

    sealed class FormException : Exception() {
        data object Download : FormException()
        data object Decoding : FormException()
    }

    private val client = OkHttpClient()

    @Throws(FormException::class)
    fun getForm(formId: String): Form {
        val request = Request.Builder().url("https://api.typeform.com/forms/$formId").build()

        val response = try {
            client.newCall(request).execute()
        } catch (_: Exception) {
            throw FormException.Decoding
        }

        val body = response.body ?: throw FormException.Download

        try {
            return Typeform.json.decodeFromString(body.string())
        } catch (_: Exception) {
            throw FormException.Decoding
        }
    }
}
