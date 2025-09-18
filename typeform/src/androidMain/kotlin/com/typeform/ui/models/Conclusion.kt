package com.typeform.ui.models

import com.typeform.models.Responses
import com.typeform.schema.structure.EndingScreen

sealed class Conclusion {
    /**
     * The [com.typeform.schema.Form] was completed to a defined endpoint.
     */
    data class Completed(val responses: Responses, val endingScreen: EndingScreen) : Conclusion()

    /**
     * The [com.typeform.schema.Form] was abandoned after the [com.typeform.schema.structure.WelcomeScreen] and before a [com.typeform.schema.structure.ThankYouScreen].
     */
    data class Abandoned(val responses: Responses) : Conclusion()

    /**
     * The [com.typeform.schema.Form] was rejected due to invalid form logic & navigation.
     */
    data class Rejected(val responses: Responses) : Conclusion()

    /**
     * The [com.typeform.schema.Form] was cancelled without moving past a [com.typeform.schema.structure.WelcomeScreen].
     */
    object Canceled : Conclusion()
}
