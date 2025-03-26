package com.example.jpc_google_maps_demo.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.jpc_google_maps_demo.map.MapController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapAppScreen(
    mapController: MapController,
    onSearchClick: () -> Unit
) {
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
    }
}