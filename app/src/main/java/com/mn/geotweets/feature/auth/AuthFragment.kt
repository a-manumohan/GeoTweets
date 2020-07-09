package com.mn.geotweets.feature.auth

import android.view.View
import androidx.navigation.fragment.navArgs
import com.mn.geotweets.R
import com.mn.geotweets.feature.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_auth.*

class AuthFragment : BaseFragment() {

    private val authViewModel by lazy { viewModel() as AuthViewModel }

    override fun layoutId() = R.layout.fragment_auth

    override fun viewCreated(view: View) {
        application.applicationComponent.authComponent.inject(this)

        withViewModel(authViewModel)
            .state(::handleState)
            .event(::handleEvent)
            .error(::handleError)

        setupViews()
    }

    override fun onResume() {
        super.onResume()
        authViewModel.fetchAuthToken()
    }

    private fun setupViews() {
        authorizeTwitter.setOnClickListener {
            authViewModel.authorizeTwitter()
        }
    }

    private fun handleState(state: Auth.State) {

    }

    private fun handleEvent(event: Auth.Event) {
        when (event) {
            is Auth.Event.EnableAuthButton -> authorizeTwitter.isEnabled = event.enable
            is Auth.Event.Authorize -> openUrl(event.url)
        }
    }

    private fun handleError(error: Auth.Error) {
        when (error) {
            Auth.Error.NetworkError -> showToast(R.string.network_error)
            Auth.Error.ServerError -> showToast(R.string.something_went_wrong)
            Auth.Error.UnknownError -> showToast(R.string.something_went_wrong)
            Auth.Error.Unauthorized -> showToast(R.string.auth_failed)
            is Auth.Error.GenericError -> showToast(error.message)
        }
    }
}