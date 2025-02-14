package com.mn.geotweets.feature.details

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.mn.geotweets.R
import com.mn.geotweets.feature.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tweet_details.*

class TweetDetailsFragment : BaseFragment() {
    private val args: TweetDetailsFragmentArgs by navArgs()

    private val tweetDetailsViewModel by lazy { viewModel() as TweetDetailsViewModel }

    override fun layoutId() = R.layout.fragment_tweet_details

    override fun viewCreated(view: View) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        toolbar.setupWithNavController(findNavController())
        toolbar.title = ""

        application.applicationComponent.mainComponent.inject(this)
        withViewModel(tweetDetailsViewModel)
            .state(::handleState)
            .event(::handleEvent)
            .error(::handleError)

        tweetDetailsView.onPlayVideo = {
            tweetDetailsViewModel.playVideo()
        }
        tweetDetailsView.onRetweet = {
            tweetDetailsViewModel.retweet()
        }

        tweetDetailsViewModel.fetchTweetDetails(args.tweetId)
    }

    private fun handleState(state: TweetDetails.State) {
        when (state) {
            is TweetDetails.State.Details -> {
                toolbar.title = state.uiTweetDetails.userName
                tweetDetailsView.bind(state.uiTweetDetails)
            }
        }
    }

    private fun handleEvent(event: TweetDetails.Event) {
        when (event) {
            is TweetDetails.Event.PlayVideo -> showVideoDialog(event.url)
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

    private fun showVideoDialog(url: String) {
        val dialog = VideoDialogFragment.newInstance(url)
        dialog.show(childFragmentManager, "video")
    }
}