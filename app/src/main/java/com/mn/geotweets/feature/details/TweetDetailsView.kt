package com.mn.geotweets.feature.details

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.mn.geotweets.R
import kotlinx.android.synthetic.main.view_tweet_details.view.*

class TweetDetailsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var onPlayVideo = {}
    var onRetweet = {}

    init {
        inflate(context, R.layout.view_tweet_details, this)
        playVideo.setOnClickListener { onPlayVideo() }
        retweet.setOnClickListener { onRetweet() }
    }

    fun bind(uiTweetDetails: UiTweetDetails) {
        tweetText.text = uiTweetDetails.text
        userName.text = uiTweetDetails.userName
        time.text = uiTweetDetails.date
        playVideo.visibility = when (uiTweetDetails.showPlayButton) {
            true -> View.VISIBLE
            false -> View.GONE
        }
        uiTweetDetails.imageUrl?.let {
            tweetImage.visibility = View.VISIBLE
            Glide.with(this).load(it).centerInside().into(tweetImage)
        }

        val retweetIconId = when (uiTweetDetails.isReTweeted) {
            true -> R.drawable.ic_retweet_active
            false -> R.drawable.ic_retweet
        }
        retweet.setImageDrawable(ContextCompat.getDrawable(context, retweetIconId))
    }
}