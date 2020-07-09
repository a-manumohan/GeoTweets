package com.mn.geotweets.feature.auth

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mn.geotweets.R
import com.mn.geotweets.feature.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_auth_result.*

class AuthResultFragment : BaseFragment() {
    private val args: AuthResultFragmentArgs by navArgs()
    private val authResultViewModel by lazy { viewModel() as AuthResultViewModel }

    override fun layoutId() = R.layout.fragment_auth_result

    override fun viewCreated(view: View) {
        application.applicationComponent.authComponent.inject(this)

        withViewModel(authResultViewModel)
            .event(::handleEvent)
            .error(::handleError)
        authorizeAgain.setOnClickListener {
            val direction = AuthResultFragmentDirections.actionAuthResultFragmentToAuthFragment()
            findNavController().navigate(direction)
        }
        authResultViewModel.authTokensReceived(args.oauthToken, args.oauthVerifier)
    }

    private fun handleEvent(event: AuthResult.Event) {
        when (event) {
            AuthResult.Event.GoToMap -> {
                val direction =
                    AuthResultFragmentDirections.actionAuthResultFragmentToTweetMapFragment()
                findNavController().navigate(direction)
            }
        }
    }

    private fun handleError(error: AuthResult.Error) {
        authorizeAgain.visibility = View.VISIBLE

        val message = when (error) {
            AuthResult.Error.NetworkError -> getString(R.string.network_error)
            AuthResult.Error.ServerError -> getString(R.string.something_went_wrong)
            AuthResult.Error.UnknownError -> getString(R.string.something_went_wrong)
            AuthResult.Error.Unauthorized -> getString(R.string.auth_failed)
            is AuthResult.Error.GenericError -> error.message
        }

        noticeLabel.text = message
    }
}