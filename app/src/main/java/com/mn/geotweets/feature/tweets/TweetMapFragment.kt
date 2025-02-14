package com.mn.geotweets.feature.tweets

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.mn.geotweets.R
import com.mn.geotweets.feature.base.BaseFragment

class TweetMapFragment : BaseFragment() {
    private val tweetMapViewModel by lazy { viewModel() as TweetMapViewModel }

    private lateinit var googleMap: GoogleMap
    private var bundle: Bundle? = null
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null

    override fun layoutId() = R.layout.fragment_tweet_map

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.bundle = savedInstanceState
    }

    override fun viewCreated(view: View) {
        application.applicationComponent.mainComponent.inject(this)
        setupMap()

        withViewModel(tweetMapViewModel)
            .state(::handleState)
            .event(::handleEvent)
            .error(::handleError)
    }

    private fun handleState(state: TweetMap.State) {
        when (state) {
            is TweetMap.State.Tweets -> addTweetsToMap(state.uiTweets)
        }
    }

    private fun handleEvent(event: TweetMap.Event) {
        when (event) {
            is TweetMap.Event.GoToDetails -> {
                val direction =
                    TweetMapFragmentDirections
                        .actionTweetMapFragmentToTweetDetailsFragment(event.tweetId)
                findNavController().navigate(direction)
            }
        }
    }

    private fun handleError(error: TweetMap.Error) {
        when (error) {
            TweetMap.Error.NetworkError -> showToast(R.string.network_error)
            TweetMap.Error.ServerError -> showToast(R.string.something_went_wrong)
            TweetMap.Error.UnknownError -> showToast(R.string.something_went_wrong)
            TweetMap.Error.Unauthorized -> {
                val direction = TweetMapFragmentDirections.actionTweetMapFragmentToAuthFragment()
                findNavController().navigate(direction)
            }
            is TweetMap.Error.GenericError -> showToast(error.message)
            is TweetMap.Error.Location -> handleLocationError(error)
        }
    }

    private fun handleLocationError(error: TweetMap.Error.Location) {
        when (error) {
            TweetMap.Error.Location.Denied -> showToast(R.string.location_denied)
            TweetMap.Error.Location.Failed -> showToast(R.string.location_failed)
        }
    }

    private fun setupMap() {
        (childFragmentManager.findFragmentById(R.id.tweetsMap) as SupportMapFragment).getMapAsync {
            googleMap = it
            getLocationPermission()
        }
    }

    private fun addTweetsToMap(uiTweets: List<UiTweet>) {
        googleMap.clear()
        val builder = LatLngBounds.Builder()
        uiTweets.forEach {
            val location = LatLng(it.lat, it.lng)
            builder.include(location)
            val options = MarkerOptions()
                .title(it.text)
                .position(location)
                .snippet(it.username)
            val marker = googleMap.addMarker(options)
            marker.tag = it.id
        }
        googleMap.setOnInfoWindowClickListener { tweetMapViewModel.onMarkerClicked(it.tag as String) }

        if (uiTweets.isNotEmpty()) {
            val userLocationLatLng =
                LatLng(lastKnownLocation?.latitude ?: 0.0, lastKnownLocation?.longitude ?: 0.0)
            builder.include(userLocationLatLng)
            val cUpdate = CameraUpdateFactory.newLatLngBounds(builder.build(), PADDING)
            googleMap.animateCamera(cUpdate)
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
            updateMap()
            getDeviceLocation()
        } else {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                RC_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        locationPermissionGranted = false
        when (requestCode) {
            RC_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    locationPermissionGranted = true
                    updateMap()
                    getDeviceLocation()
                } else {
                    tweetMapViewModel.locationDenied()
                }
            }
        }

    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        if (locationPermissionGranted) {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    lastKnownLocation = task.result
                    lastKnownLocation?.let {
                        moveMapToLocation(it)
                        tweetMapViewModel.locationReceived(it)
                    } ?: tweetMapViewModel.locationFailed()
                } else {
                    tweetMapViewModel.locationFailed()
                }
            }
        }
    }

    private fun moveMapToLocation(lastKnownLocation: Location) {
        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    lastKnownLocation.latitude,
                    lastKnownLocation.longitude
                ), ZOOM.toFloat()
            )
        )
    }

    @SuppressLint("MissingPermission")
    private fun updateMap() {
        googleMap.isMyLocationEnabled = true
        googleMap.uiSettings.isMyLocationButtonEnabled = true
    }

//    private fun showLocationRequiredDialog() {
//        AlertDialog.Builder(requireContext())
//            .setTitle(getString(R.string.title_location_permission))
//            .setMessage(getString(R.string.message_location_permission))
//            .setPositiveButton(R.string.ok) { _, _ -> action() }
//            .setNegativeButton(R.string.cancel) { _, _ -> requireActivity().finish() }
//            .setCancelable(false)
//            .show()
//    }

    companion object {
        private const val RC_LOCATION_PERMISSION = 1000
        private const val ZOOM = 5
        private const val PADDING = 150
    }
}