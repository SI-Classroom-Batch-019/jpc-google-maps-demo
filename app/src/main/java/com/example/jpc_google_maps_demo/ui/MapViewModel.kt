package com.example.jpc_google_maps_demo.ui

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapViewModel : ViewModel() {
    private val _selectedPlace = MutableStateFlow<SelectedPlace?>(null)
    val selectedPlace = _selectedPlace.asStateFlow()

    fun updatePlace(place: SelectedPlace) {
        _selectedPlace.value = place
    }
}

data class SelectedPlace(
    val name: String,
    val address: String,
    val rating: String,
    val latLng: LatLng
)