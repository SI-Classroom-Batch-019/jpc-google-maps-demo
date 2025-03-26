package com.example.jpc_google_maps_demo.ui.screens

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng

@Composable
fun MapScreen() {
    val context = LocalContext.current
    val mapView = remember { MapView(context) }

    AndroidView(factory = {
        mapView.onCreate(Bundle())
        mapView.onResume()

        mapView.getMapAsync { map ->
            map.uiSettings.isZoomControlsEnabled = true
            val berlin = LatLng(52.52, 13.405)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(berlin, 12f))
        }
        mapView
    })
}