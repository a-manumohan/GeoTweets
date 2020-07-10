package com.mn.geotweets.feature.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.mn.geotweets.R
import com.mn.geotweets.feature.base.BaseFragment

class TweetMapFragment : BaseFragment() {
    private val tweetMapViewModel by lazy { viewModel() as TweetMapViewModel }

    private lateinit var googleMap: GoogleMap
    private var bundle: Bundle? = null
    private var locationPermissionGranted = false

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

    private fun handleState(state: TweetMap.State) {}
    private fun handleEvent(event: TweetMap.Event) {}
    private fun handleError(error: TweetMap.Error) {}

    private fun setupMap() {
        (childFragmentManager.findFragmentById(R.id.tweetsMap) as SupportMapFragment).getMapAsync {
            googleMap = it
            getLocationPermission()
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
                } else {
                    TODO("location denied")
                }
            }
        }
        updateMap()
        getDeviceLocation()
    }

    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity());
        var lastKnownLocation: Location?
        if (locationPermissionGranted) {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    lastKnownLocation = task.result
                    if (lastKnownLocation != null) {
                        googleMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(
                                    lastKnownLocation!!.latitude,
                                    lastKnownLocation!!.longitude
                                ), 5.toFloat()
                            )
                        )
                    }
                }
            }
        }
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
    }
}