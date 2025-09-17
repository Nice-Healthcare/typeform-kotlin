package com.typeform

import com.typeform.schema.Choice
import com.typeform.schema.Form
import com.typeform.schema.translation.TranslatedForm

open class TypeformTestCase {
    companion object {
        fun <T : Any> assertUnwrap(
            actual: T?,
            message: String? = null,
        ): T {
            return actual ?: throw AssertionError(message)
        }

        const val VISIT_REASON = "aea7a268-64d4-4f16-920a-b9afe317e3b6"
        const val VISIT_STATE = "508ea9df-177c-4cda-8371-8f7cc1bc60a2"
        const val PATIENT_AGE = "4915db69-55ca-4a00-b57e-893d7ea3e761"
        const val PATIENT_BIOLOGICAL_SEX = "c09778dd-d584-40cc-8517-a627592ca5f1"

        const val DATE = "62bbe9cc-c797-4b7a-ad1d-d4328f7f8589"
        const val DROPDOWN = "508ea9df-177c-4cda-8371-8f7cc1bc60a2"
        const val GROUP = "778d214e-b9e1-4fca-a0ed-922369858b36"
        const val LONG_TEXT = "eeab74bc-284c-4dc7-a948-4ba045980ccf"
        const val MULTIPLE_CHOICE_MANY = "ab337720-ca51-402a-aa47-8ec8f316ba46"
        const val MULTIPLE_CHOICE_ONE = "aea7a268-64d4-4f16-920a-b9afe317e3b6"
        const val NUMBER = "3e8760df-4a6e-47f2-8b03-0ef2e72ac35f"
        const val RATING = "7f117917-1c53-4524-a334-fe3f60e229dd"
        const val SHORT_TEXT = "d7a86703-22e8-495b-95b2-543cd3f7dde6"
        const val YES_NO = "5d99768b-65af-4f68-9939-87dfbd29f49a"
        const val STATEMENT = "8cd03d7e-412f-4be4-9e80-281f66675fca"

        val minnesota = Choice(
            id = "ki7l02wXkJJB",
            ref = "aa028c7c-ce34-428f-8563-35bce5201dc1",
            label = "Minnesota",
        )

        val adult = Choice(
            id = "zkoKtHgiYyKt",
            ref = "a66c1065-4e4f-46fc-8a26-794cc46a59f9",
            label = "Adult, 18-64 years of age",
        )

        val male = Choice(
            id = "FRF7t7yJYhEd",
            ref = "a5e3cd94-58f6-4701-8632-dc3cc030dde5",
            label = "Male",
        )

        val acute =
            @Suppress("ktlint:standard:max-line-length")
            Choice(
                id = "9xUQhXrfKjLP",
                ref = "3faf5f88-5171-4933-af0f-210716bf1a60",
                label = "A health issue like an illness or injury that is new, started recently (aka \"Acute\"), and you haven't discussed with a Specialty medical provider in the past (examples include sore throat, cough, urinary symptoms, etc.)",
            )
    }

    val resources: Resources = Resources()
    val form: Form

    open val jsonResource: String
        get() = "MedicalIntake23.json"

    init {
        val formBytes = resources.contentOfResource(jsonResource)
        val json = String(formBytes)
        form = Typeform.json.decodeFromString(json)
    }

    fun decodeFormFromResource(named: String): Form {
        val bytes = resources.contentOfResource(named)
        val json = String(bytes)
        return Typeform.json.decodeFromString(json)
    }

    fun decodeTranslationFromResource(named: String): TranslatedForm {
        val bytes = resources.contentOfResource(named)
        val json = String(bytes)
        return Typeform.json.decodeFromString(json)
    }
}
