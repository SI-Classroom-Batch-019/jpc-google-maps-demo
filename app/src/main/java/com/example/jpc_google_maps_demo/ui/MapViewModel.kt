package com.example.jpc_google_maps_demo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.net.URLEncoder
import com.example.jpc_google_maps_demo.BuildConfig

class MapViewModel: ViewModel() {

    fun validateAddress(address: String, callback: (LatLng?) -> Unit) {
        viewModelScope.launch {
            try {
                val apiKey = BuildConfig.GOOGLE_MAPS_API_KEY
                val url = "https://maps.googleapis.com/maps/api/geocode/json?" +
                        "address=${URLEncoder.encode(address, "UTF-8")}&key=YOUR_API_KEY"

                val result = URL(url).readText()
                val json = JSONObject(result)
                val location = json.getJSONArray("results")
                    .optJSONObject(0)
                    ?.getJSONObject("geometry")
                    ?.getJSONObject("location")

                val latLng = location?.let {
                    LatLng(it.getDouble("lat"), it.getDouble("lng"))
                }

                withContext(Dispatchers.Main) {
                    callback(latLng)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) { callback(null) }
            }
        }
    }
}