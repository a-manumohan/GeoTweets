package com.mn.geotweets.feature.auth

import com.mn.domain.usecase.auth.GetAuthToken
import com.mn.geotweets.feature.base.BaseViewModel
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val getAuthToken: GetAuthToken
) : BaseViewModel<Auth.State, Auth.Event, Auth.Error>() {
    init {
        getAuthToken {

        }
    }
}