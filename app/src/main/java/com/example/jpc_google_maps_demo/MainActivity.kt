package com.example.jpc_google_maps_demo

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.jpc_google_maps_demo.map.GeocodeHelper
import com.example.jpc_google_maps_demo.ui.screens.MapAppScreen
import com.example.jpc_google_maps_demo.ui.theme.JpcgooglemapsdemoTheme
import com.example.jpc_google_maps_demo.map.MapController
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode

const val TAG = "DEBUG"

class MainActivity : ComponentActivity() {
    private lateinit var placeLauncher: ActivityResultLauncher<Intent>
    private val mapController = MapController()
    private val geocodeHelper = GeocodeHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Initialize Places API with API Key from Manifest or Config
        initializePlacesApi()

        // 3. Setup Place Search Launcher
        setupPlaceSearchLauncher()

        // 4. Enable Edge to Edge
        enableEdgeToEdge()

        // 5. Set Content
        setContent {
            JpcgooglemapsdemoTheme {
                MapAppScreen(
                    mapController = mapController,
                    onSearchClick = { launchPlaceSearch() }
                )
            }
        }
    }

    private fun initializePlacesApi() {
        // Check if Places is already initialized
        if (Places.isInitialized()) {
            Log.d(TAG, "Places API already initialized.")
            return
        }
        // Fetch API Key from the manifest metadata
        val apiKey = try {
            applicationContext.packageManager
                .getApplicationInfo(packageName, PackageManager.GET_META_DATA)
                .metaData
                .getString("com.google.android.geo.API_KEY")
        } catch (e: Exception) {
            Log.e(TAG, "Error getting API key from metadata: ${e.message}")
            null
        }
        // Initialize Places API using the API key. If not found, use a hardcoded placeholder (for debugging only)
        if (apiKey != null && apiKey.isNotEmpty()) {
            Places.initialize(applicationContext, apiKey)
            Log.d(TAG, "Places API initialized with API key from manifest.")
        } else {
            // REMOVE THIS in production code, this is for local testing purposes only.
            Log.w(
                TAG,
                "API key not found in manifest or it is empty. Using placeholder for testing. " +
                        "THIS SHOULD NOT BE IN PRODUCTION CODE."
            )
        }
    }

    private fun setupPlaceSearchLauncher() {
        placeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    // Handle successful place selection
                    val place = Autocomplete.getPlaceFromIntent(result.data!!)
                    Log.e(TAG, "Autocomplete Handle successful place selection:\n $place")
                    //handlePlaceSelection(place)
                    onPlaceSelected(place)
                }

                AutocompleteActivity.RESULT_ERROR -> {
                    // Handle errors from Autocomplete
                    val status = Autocomplete.getStatusFromIntent(result.data!!)
                    Log.e(TAG, "Autocomplete error: ${status.statusMessage}")
                    //Show a error message to the user if appropriate
                }

                Activity.RESULT_CANCELED -> {
                    // User canceled the operation
                    Log.d(TAG, "Autocomplete canceled by user")
                }
            }
        }
    }

    private fun launchPlaceSearch() {
        val fields = listOf(
            Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,
            Place.Field.LAT_LNG
        )
        val intent = Autocomplete
            .IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
            .build(this)
        placeLauncher.launch(intent)
    }

    private fun onPlaceSelected(place: Place) {
        Log.d(TAG, "Selected Address: ${place.address}")
        Log.d(TAG, "Selected LatLng: ${place.latLng}")
        Log.i(TAG, "place.formattedAddress: ${place.formattedAddress}")
        Log.i(TAG, "place.address.deprecated: ${place.address}")

        val address = place.address ?: return
        geocodeHelper.validateAddress(address) { validatedLatLng ->
            Log.i(TAG, "geocodeHelper.validateAddress.validatedLatLng: $validatedLatLng")
            validatedLatLng?.let {
                mapController.moveTo(it, place.name ?: "Selected")
            }
        }
    }
}