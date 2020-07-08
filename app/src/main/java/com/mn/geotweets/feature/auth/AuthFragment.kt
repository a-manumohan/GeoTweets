package com.mn.geotweets.feature.auth

import android.view.View
import com.mn.geotweets.R
import com.mn.geotweets.feature.base.BaseFragment

class AuthFragment : BaseFragment() {

    private val authViewModel by lazy { viewModel() as AuthViewModel }

    override fun layoutId() = R.layout.fragment_auth

    override fun viewCreated(view: View) {
        application.applicationComponent.authComponent.inject(this)

        withViewModel(authViewModel)
            .state(::handleState)
            .event(::handleEvent)
            .error(::handleError)
    }

    private fun handleState(state: Auth.State) {

    }

    private fun handleEvent(event: Auth.Event) {

    }

    private fun handleError(error: Auth.Error) {

    }
}