package com.vkasurinen.gamestore2.presentation.onboarding


import androidx.lifecycle.ViewModel

class OnBoardingViewModel : ViewModel() {

    fun onEvent(event: onBoardingEvent) {
        when (event) {
            onBoardingEvent.NavigateToMainScreen -> {
                // Handle navigation to main screen
            }
        }
    }
}