package com.typeform.adapters

import com.typeform.schema.Field
import com.typeform.schema.Form
import com.typeform.schema.FormType
import com.typeform.schema.Links
import com.typeform.schema.Logic
import com.typeform.schema.Settings
import com.typeform.schema.ThankYouScreen
import com.typeform.schema.Theme
import com.typeform.schema.WelcomeScreen
import com.typeform.schema.Workspace

/**
 * A version of [Form] with simplified & flattened structures that has been optimized
 * for out-of-the-box de/serialization.
 *
 * An example using Moshi:
 * ```kotlin
 * class TypeformAdapter {
 *   @ToJson
 *   fun toJson(type: Form): FlatForm {
 *     return FlatForm.make(type)
 *   }
 *
 *   @FromJson
 *   fun fromJson(rawValue: FlatForm): Form {
 *     return Form.make(rawValue)
 *   }
 * }
 * ```
 */
data class FlatForm(
    val id: String,
    val type: FormType,
    val logic: List<FlatLogic>,
    val theme: Theme,
    val title: String,
    val _links: Links,
    val fields: List<FlatField>,
    val hidden: List<String>?,
    val settings: Settings,
    val workspace: Workspace,
    val welcome_screens: List<WelcomeScreen>?,
    val thankyou_screens: List<ThankYouScreen>,
) {
    companion object {
    }
}

fun Form.Companion.make(flatForm: FlatForm): Form {
    return Form(
        id = flatForm.id,
        type = flatForm.type,
        logic = flatForm.logic.map { Logic.make(it) },
        theme = flatForm.theme,
        title = flatForm.title,
        _links = flatForm._links,
        fields = flatForm.fields.map { Field.make(it) },
        hidden = flatForm.hidden,
        settings = flatForm.settings,
        workspace = flatForm.workspace,
        welcome_screens = flatForm.welcome_screens,
        thankyou_screens = flatForm.thankyou_screens,
    )
}

fun FlatForm.Companion.make(form: Form): FlatForm {
    return FlatForm(
        id = form.id,
        type = form.type,
        logic = form.logic.map { FlatLogic.make(it) },
        theme = form.theme,
        title = form.title,
        _links = form._links,
        fields = form.fields.map { FlatField.make(it) },
        hidden = form.hidden,
        settings = form.settings,
        workspace = form.workspace,
        welcome_screens = form.welcome_screens,
        thankyou_screens = form.thankyou_screens,
    )
}
