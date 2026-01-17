package com.typeform

import kotlin.test.Test
import kotlin.test.assertEquals

class FormDecodingTests {

    val resources: Resources = Resources()

    @Test
    fun matrixExampleFormDecoding() {
        val form = resources.form("MatrixExample.json")
        assertEquals("WkORBn0g", form.id)
    }
}
