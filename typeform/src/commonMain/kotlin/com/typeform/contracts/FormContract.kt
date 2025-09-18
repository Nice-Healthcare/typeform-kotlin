package com.typeform.contracts

import com.typeform.schema.Form
import com.typeform.schema.FormType
import com.typeform.schema.Links
import com.typeform.schema.Settings
import com.typeform.schema.Theme
import com.typeform.schema.Workspace
import com.typeform.schema.structure.EndingScreen
import com.typeform.schema.structure.WelcomeScreen
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A version of [Form] with simplified & flattened structures that has been optimized
 * for out-of-the-box de/serialization using **kotlinx-serialization**.
 */
@Serializable
data class FormContract(
    val id: String,
    val type: FormType,
    val logic: List<LogicContract>?,
    val theme: Theme,
    val title: String,
    @SerialName("_links")
    val links: Links,
    val fields: List<FieldContract>,
    val hidden: List<String>?,
    val settings: Settings,
    val workspace: Workspace,
    @SerialName("welcome_screens")
    val welcomeScreens: List<WelcomeScreen>?,
    @SerialName("thankyou_screens")
    val thankYouScreens: List<EndingScreen>,
) {
    constructor(form: Form) : this(
        id = form.id,
        type = form.type,
        logic = form.logic.map { LogicContract(it) },
        theme = form.theme,
        title = form.title,
        links = form.links,
        fields = form.fields.map { FieldContract(it) },
        hidden = form.hidden,
        settings = form.settings,
        workspace = form.workspace,
        welcomeScreens = form.welcomeScreens,
        thankYouScreens = form.endingScreens,
    )

    fun toForm(): Form {
        return Form(
            id = id,
            type = type,
            logic = (logic ?: emptyList()).map { it.toLogic() },
            theme = theme,
            title = title,
            links = links,
            fields = fields.map { it.toField() },
            hidden = hidden,
            settings = settings,
            workspace = workspace,
            welcomeScreens = welcomeScreens,
            endingScreens = thankYouScreens,
        )
    }
}
