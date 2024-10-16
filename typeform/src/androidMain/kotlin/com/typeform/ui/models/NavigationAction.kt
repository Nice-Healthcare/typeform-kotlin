package com.typeform.ui.models

import com.typeform.models.Position

sealed class NavigationAction {
    data class PositionAction(val position: Position) : NavigationAction()

    data class ConclusionAction(val conclusion: Conclusion) : NavigationAction()

    data object Back : NavigationAction()
}
