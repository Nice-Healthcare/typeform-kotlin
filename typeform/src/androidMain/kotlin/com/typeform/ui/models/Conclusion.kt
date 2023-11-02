package com.typeform.ui.models

import com.typeform.models.Responses
import com.typeform.schema.ThankYouScreen

sealed class Conclusion {
    /**
     * The [com.typeform.schema.Form] was completed to a defined endpoint.
     */
    data class Completed(val responses: Responses, val thankYouScreen: ThankYouScreen) : Conclusion()

    /**
     * The [com.typeform.schema.Form] was abandoned after the [com.typeform.schema.WelcomeScreen] and before a [com.typeform.schema.ThankYouScreen].
     */
    data class Abandoned(val responses: Responses) : Conclusion()

    /**
     * The [com.typeform.schema.Form] was rejected due to invalid form logic & navigation.
     */
    data class Rejected(val responses: Responses) : Conclusion()

    /**
     * The [com.typeform.schema.Form] was cancelled without moving past a [com.typeform.schema.WelcomeScreen].
     */
    object Canceled : Conclusion()
}
