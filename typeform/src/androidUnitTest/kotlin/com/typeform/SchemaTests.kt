package com.typeform

import com.typeform.adapters.FlatForm
import com.typeform.adapters.make
import com.typeform.schema.Form
import com.typeform.schema.WelcomeScreen
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

class SchemaTests : TypeformTestCase() {

    @Test
    fun testFormDecoding() {
        val flatFormAdapter = moshi.adapter(FlatForm::class.java)
        val flatForm = assertUnwrap(flatFormAdapter.fromJson(json))

        assertEquals(Form.make(flatForm), form)

        assertEquals(152, form.logic.count())
        assertEquals(56, form.fields.count())
    }

    @Test
    fun testFirstScreen() {
        when (val firstScreen = assertUnwrap(form.firstScreen)) {
            is WelcomeScreen -> {
                assertEquals("DmQFaO34DUcM", firstScreen.id)
                assertEquals("122872b0-4de9-42c2-9204-fdec922538af", firstScreen.ref)
                assertTrue(firstScreen.title.startsWith("Hello and thanks for reaching out"))
                assertEquals("Start", firstScreen.properties.button_text)
            }
            else -> {
                fail("Incorrect First Screen Type")
            }
        }
    }

    @Test
    fun testDefaultOrFirstThankYouScreen() {
        val defaultThankYouScreen = assertUnwrap(form.defaultOrFirstThankYouScreen)
        assertEquals("DefaultTyScreen", defaultThankYouScreen.id)
        assertEquals("default_tys", defaultThankYouScreen.ref)
        assertEquals("All done! Thanks for your time.", defaultThankYouScreen.title)
    }
}
