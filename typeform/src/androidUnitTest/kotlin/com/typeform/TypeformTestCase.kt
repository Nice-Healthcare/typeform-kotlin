package com.typeform

import com.typeform.schema.Choice
import com.typeform.schema.Form

open class TypeformTestCase {
    companion object {
        fun <T : Any> assertUnwrap(actual: T?, message: String? = null): T {
            return actual ?: throw AssertionError(message)
        }

        const val visitReason = "aea7a268-64d4-4f16-920a-b9afe317e3b6"
        const val visitState = "508ea9df-177c-4cda-8371-8f7cc1bc60a2"
        const val patientAge = "4915db69-55ca-4a00-b57e-893d7ea3e761"
        const val patientBiologicalSex = "c09778dd-d584-40cc-8517-a627592ca5f1"

        const val date = "62bbe9cc-c797-4b7a-ad1d-d4328f7f8589"
        const val dropdown = "508ea9df-177c-4cda-8371-8f7cc1bc60a2"
        const val group = "778d214e-b9e1-4fca-a0ed-922369858b36"
        const val longText = "eeab74bc-284c-4dc7-a948-4ba045980ccf"
        const val multipleChoice_Many = "ab337720-ca51-402a-aa47-8ec8f316ba46"
        const val multipleChoice_One = "aea7a268-64d4-4f16-920a-b9afe317e3b6"
        const val number = "3e8760df-4a6e-47f2-8b03-0ef2e72ac35f"
        const val rating = "7f117917-1c53-4524-a334-fe3f60e229dd"
        const val shortText = "d7a86703-22e8-495b-95b2-543cd3f7dde6"
        const val yesNo = "5d99768b-65af-4f68-9939-87dfbd29f49a"
        const val statement = "8cd03d7e-412f-4be4-9e80-281f66675fca"

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

        val acute = Choice(
            id = "9xUQhXrfKjLP",
            ref = "3faf5f88-5171-4933-af0f-210716bf1a60",
            /* ktlint-disable max-line-length */
            label = "A health issue like an illness or injury that is new, started recently (aka \"Acute\"), and you haven't discussed with a Specialty medical provider in the past (examples include sore throat, cough, urinary symptoms, etc.)",
            /* ktlint-enable max-line-length */
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
}
