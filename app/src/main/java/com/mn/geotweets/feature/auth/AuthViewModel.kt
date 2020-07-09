package com.mn.geotweets.feature.auth

import com.mn.domain.common.Failure
import com.mn.domain.usecase.auth.AuthToken
import com.mn.domain.usecase.auth.GetAuthToken
import com.mn.geotweets.BuildConfig
import com.mn.geotweets.feature.base.BaseViewModel
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val getAuthToken: GetAuthToken
) : BaseViewModel<Auth.State, Auth.Event, Auth.Error>() {

    private var authToken: AuthToken? = null

    fun fetchAuthToken() {
        getAuthToken(CALLBACK_URL) {
            it.either(::handleFailure, ::handleAuthToken)
        }
    }

    private fun handleFailure(failure: Failure) {
        val err = when (failure) {
            Failure.NetworkError -> Auth.Error.NetworkError
            Failure.UnknownError -> Auth.Error.UnknownError
            is Failure.GenericException -> Auth.Error.GenericError(
                failure.messages.joinToString("\n")
            )
            Failure.ServerError.Unauthorized -> Auth.Error.Unauthorized
            else -> Auth.Error.ServerError
        }
        error(err)
    }

    private fun handleAuthToken(authToken: AuthToken) {
        this.authToken = authToken
        with(authToken) {
            if (oathToken.isNotBlank() && oathTokenSecret.isNotBlank() && oathCallBackConfirmed) {
                event(Auth.Event.EnableAuthButton(true))
            } else {
                error(Auth.Error.Unauthorized)
            }
        }
    }

    private fun getAuthUrl(authToken: AuthToken): String {
        val baseUrl = BuildConfig.BASE_URL
        return "$baseUrl/oauth/authorize?oauth_token=${authToken.oathToken}"
    }

    fun authorizeTwitter() {
        authToken?.let {
            val authUrl = getAuthUrl(it)
            event(Auth.Event.Authorize(authUrl))
        }
    }

    companion object {
        private const val CALLBACK_URL = "geotweets://authorize/"
    }
}