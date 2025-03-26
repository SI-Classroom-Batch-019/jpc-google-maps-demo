package com.example.jpc_google_maps_demo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jpc_google_maps_demo.map.MapController
import com.example.jpc_google_maps_demo.ui.MapViewModel
import com.example.jpc_google_maps_demo.ui.SelectedPlace

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapAppScreen(
    mapController: MapController,
    viewModel: MapViewModel,
    onSearchClick: () -> Unit
) {
    val selectedPlace = viewModel.selectedPlace.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Google Maps Demo") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onSearchClick) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            MapScreen(mapController = mapController)
        }
        if (selectedPlace.value != null && selectedPlace.value != null) {
            BottomCenterCard(selectedPlace.value!!)
        }
    }
}

@Composable
fun BottomCenterCard(selectedPlace: SelectedPlace) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp, bottom = 100.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("📍 ${selectedPlace.name}", style = MaterialTheme.typography.titleMedium)
                Text(selectedPlace.address, style = MaterialTheme.typography.bodyMedium)
                Text("⭐️${selectedPlace.rating}", style = MaterialTheme.typography.bodyMedium)
                Text(
                    "Lat: ${selectedPlace.latLng.latitude}, Lng: ${selectedPlace.latLng.longitude}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
