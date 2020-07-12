package com.mn.geotweets.feature.details

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import com.mn.geotweets.R
import com.mn.geotweets.feature.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tweet_details.*

class TweetDetailsFragment : BaseFragment() {
    private val args: TweetDetailsFragmentArgs by navArgs()

    private val tweetDetailsViewModel by lazy { viewModel() as TweetDetailsViewModel }

    override fun layoutId() = R.layout.fragment_tweet_details

    override fun viewCreated(view: View) {
        application.applicationComponent.mainComponent.inject(this)

        NavigationUI.setupActionBarWithNavController(
            requireActivity() as AppCompatActivity,
            findNavController()
        )

        withViewModel(tweetDetailsViewModel)
            .state(::handleState)
            .event(::handleEvent)
            .error(::handleError)

        tweetDetailsView.onPlayVideo = {
            tweetDetailsViewModel.playVideo()
        }

        tweetDetailsViewModel.fetchTweetDetails(args.tweetId)
    }

    private fun handleState(state: TweetDetails.State) {
        when (state) {
            is TweetDetails.State.Details -> tweetDetailsView.bind(state.uiTweetDetails)
        }
    }

    private fun handleEvent(event: TweetDetails.Event) {
        when (event) {
            is TweetDetails.Event.PlayVideo -> TODO()
        }
    }

    private fun handleError(error: TweetDetails.Error) {
        when (error) {
            TweetDetails.Error.NetworkError -> showToast(R.string.network_error)
            TweetDetails.Error.ServerError -> showToast(R.string.something_went_wrong)
            TweetDetails.Error.UnknownError -> showToast(R.string.something_went_wrong)
            TweetDetails.Error.Unauthorized -> {
                val direction =
                    TweetDetailsFragmentDirections.actionTweetDetailsFragmentToAuthFragment()
                findNavController().navigate(direction)
            }
            is TweetDetails.Error.GenericError -> showToast(error.message)
        }
    }
}