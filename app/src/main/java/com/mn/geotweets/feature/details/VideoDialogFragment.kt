package com.mn.geotweets.feature.details

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.mn.geotweets.R
import kotlinx.android.synthetic.main.dialog_video.*

class VideoDialogFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun setupViews() {
        val url = arguments?.getString(URL)
        url?.let {
            val uri = Uri.parse(it)
            videoView.setVideoURI(uri)
            videoView.start()
        }
    }

    companion object {
        private const val URL = "arg-url"
        fun newInstance(url: String): VideoDialogFragment {
            return VideoDialogFragment().apply {
                val args = Bundle()
                args.putString(URL, url)
                arguments = args
            }
        }
    }
}