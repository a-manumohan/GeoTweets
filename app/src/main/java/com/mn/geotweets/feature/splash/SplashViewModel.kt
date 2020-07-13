package com.mn.geotweets.feature.splash

import com.mn.domain.common.TokenManager
import com.mn.geotweets.feature.base.BaseViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor(private val tokenManager: TokenManager) :
    BaseViewModel<Splash.State, Splash.Event, Splash.Error>() {
    fun checkTokens() {
        if (tokenManager.getToken().isBlank() || tokenManager.getSecret().isBlank()) {
            event(Splash.Event.GoToAuthorization)
        } else {
            event(Splash.Event.GoToMap)
        }
    }
}