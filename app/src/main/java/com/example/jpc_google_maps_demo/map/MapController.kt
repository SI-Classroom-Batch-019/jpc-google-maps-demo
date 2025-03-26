package com.example.jpc_google_maps_demo.map

import android.util.Log
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

const val TAG = "DEBUG"

class MapController {
    var googleMap: GoogleMap? = null

    fun moveTo(location: LatLng, title: String = "Selected") {
        Log.d(TAG, "googleMap: $googleMap")
        googleMap?.clear()
        googleMap?.addMarker(MarkerOptions().position(location).title(title))
        googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }
}