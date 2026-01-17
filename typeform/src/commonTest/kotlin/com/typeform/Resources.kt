package com.typeform

import com.typeform.schema.structure.Form

expect class Resources {
    fun contentOfResource(named: String): ByteArray
}

fun Resources.form(named: String) : Form {
    val bytes = contentOfResource("MatrixExample.json")
    return Typeform.json.decodeFromString(String(bytes))
}
