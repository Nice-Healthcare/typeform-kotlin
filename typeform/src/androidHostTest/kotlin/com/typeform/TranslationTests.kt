package com.typeform

import com.typeform.schema.translation.merging
import kotlin.test.Test
import kotlin.test.assertEquals

class TranslationTests : TypeformTestCase() {

    @Test
    fun testTranslationMerge() {
        val form = decodeFormFromResource("TranslationFormBase.json")
        val translation = decodeTranslationFromResource("TranslationForm(es).json")
        val translated = form.merging(translation)

        var field = assertUnwrap(form.fieldWithRef("minor-consent-recent-weight"))
        assertEquals("What was a recent weight (in pounds)?", field.title)
        assertEquals("We use this information to safely prescribe medication. ", field.properties.description)

        field = assertUnwrap(translated.fieldWithRef("minor-consent-recent-weight"))
        assertEquals("¿Cuál era su peso reciente (en libras)?", field.title)
        assertEquals("Usamos esta información para recetar medicamentos de manera segura.", field.properties.description)
    }

    @Test
    fun testTranslationMergeAdditional() {
        val form = decodeFormFromResource("standard_typeform.json")
        val translation = decodeTranslationFromResource("translated_typeform.json")
        val translated = form.merging(translation)

        var field = assertUnwrap(form.fieldWithRef("health-history-female-pregnancy"))
        var multipleChoice = assertUnwrap(field.properties.asMultipleChoice())
        var choices = multipleChoice.choices.sortedBy { it.id }
        assertEquals(
            listOf(
                "There is no chance of pregnancy and I am not breastfeeding",
                "I am currently pregnant",
                "There is a chance of pregnancy",
                "I am currently breastfeeding",
            ),
            choices.map { it.label },
        )

        field = assertUnwrap(translated.fieldWithRef("health-history-female-pregnancy"))
        multipleChoice = assertUnwrap(field.properties.asMultipleChoice())
        choices = multipleChoice.choices.sortedBy { it.id }
        assertEquals(
            listOf(
                "No hay posibilidad de embarazo y no estoy amamantando",
                "Actualmente estoy embarazada",
                "Existe la posibilidad de embarazo",
                "Actualmente estoy amamantando",
            ),
            choices.map { it.label },
        )
    }
}
