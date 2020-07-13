package com.mn.geotweets.feature.splash

import android.os.Handler
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mn.geotweets.R
import com.mn.geotweets.feature.base.BaseFragment

class SplashFragment : BaseFragment() {
    private val splashViewModel by lazy { viewModel() as SplashViewModel }

    override fun layoutId() = R.layout.fragment_splash

    override fun viewCreated(view: View) {
        application.applicationComponent.mainComponent.inject(this)

        withViewModel(splashViewModel)
            .event(::handleEvent)

        Handler().postDelayed({
            splashViewModel.checkTokens()
        }, DELAY)
    }

    private fun handleEvent(event: Splash.Event) {
        when (event) {
            Splash.Event.GoToMap -> {
                val direction = SplashFragmentDirections.actionSplashFragmentToTweetMapFragment()
                findNavController().navigate(direction)
            }
            Splash.Event.GoToAuthorization -> {
                val direction = SplashFragmentDirections.actionSplashFragmentToAuthFragment()
                findNavController().navigate(direction)
            }
        }
    }

    companion object {
        private const val DELAY = 2000L
    }
}