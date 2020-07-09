package com.mn.geotweets.feature.auth

import com.mn.domain.common.Failure
import com.mn.domain.common.TokenManager
import com.mn.domain.usecase.auth.AccessToken
import com.mn.domain.usecase.auth.GetAccessToken
import com.mn.geotweets.feature.base.BaseViewModel
import javax.inject.Inject

class AuthResultViewModel @Inject constructor(
    private val getAccessToken: GetAccessToken,
    private val tokenManager: TokenManager
) :
    BaseViewModel<AuthResult.State, AuthResult.Event, AuthResult.Error>() {

    fun authTokensReceived(oauthToken: String, oauthVerifier: String) {
        getAccessToken(GetAccessToken.Params(oauthToken, oauthVerifier)) {
            it.either(::handleFailure, ::handleSuccess)
        }
    }

    private fun handleSuccess(accessToken: AccessToken) {
        tokenManager.putToken(accessToken.oauthToken)
        tokenManager.putSecret(accessToken.oauthTokenSecret)

        event(AuthResult.Event.GoToMap)
    }

    private fun handleFailure(failure: Failure) {
        val err = when (failure) {
            Failure.NetworkError -> AuthResult.Error.NetworkError
            Failure.UnknownError -> AuthResult.Error.UnknownError
            is Failure.GenericException -> AuthResult.Error.GenericError(
                failure.messages.joinToString("\n")
            )
            Failure.ServerError.Unauthorized -> AuthResult.Error.Unauthorized
            else -> AuthResult.Error.ServerError
        }
        error(err)
    }
}