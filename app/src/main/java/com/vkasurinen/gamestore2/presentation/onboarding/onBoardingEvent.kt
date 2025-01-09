package com.vkasurinen.gamestore2.presentation.onboarding

sealed interface onBoardingEvent {
    data object NavigateToMainScreen : onBoardingEvent
}