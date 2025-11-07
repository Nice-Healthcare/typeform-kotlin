package com.typeform.ui.models

import com.typeform.models.Position
import com.typeform.models.Responses

sealed class NavigationAction {
    data class PositionAction(
        val position: Position,
        val responses: Responses,
    ) : NavigationAction()

    data class ConclusionAction(
        val conclusion: Conclusion,
    ) : NavigationAction()

    data object Back : NavigationAction()
}
