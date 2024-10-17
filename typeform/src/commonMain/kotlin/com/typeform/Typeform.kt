package com.typeform

import kotlinx.serialization.json.Json

class Typeform {
    companion object {
        /**
         * A preconfigured **kotlinx-serialization** Json Formatter.
         */
        val json: Json
            get() = Json {
                ignoreUnknownKeys = true
                explicitNulls = false
            }
    }
}
