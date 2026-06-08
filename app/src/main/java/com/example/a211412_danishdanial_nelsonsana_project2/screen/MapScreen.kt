package com.example.a211412_danishdanial_nelsonsana_project2.screen

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color as AndroidColor
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PinConfig
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.*

@Composable
fun MapScreen(navController: NavController) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    
    // Default starting position (Kuala Lumpur)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(3.1390, 101.6869), 12f)
    }

    // Remember marker states to avoid re-creating them every recomposition
    val markerState1 = remember { MarkerState(position = LatLng(3.1390, 101.6869)) }
    val markerState2 = remember { MarkerState(position = LatLng(3.1412, 101.6850)) }
    val markerState3 = remember { MarkerState(position = LatLng(3.1370, 101.6900)) }
    val markerState4 = remember { MarkerState(position = LatLng(3.1450, 101.6800)) }

    @SuppressLint("MissingPermission")
    fun moveToCurrentLocation() {
        fusedLocationClient.getCurrentLocation(
            Priority.PRIORITY_HIGH_ACCURACY,
            CancellationTokenSource().token
        ).addOnSuccessListener { location ->
            location?.let {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(
                    LatLng(it.latitude, it.longitude), 15f
                )
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) moveToCurrentLocation()
    }

    // Request GPS permission on start
    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,

            googleMapOptionsFactory = {
                GoogleMapOptions().mapId("DEMO_MAP_ID")
            },
            properties = MapProperties(
                isMyLocationEnabled = ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ),
            uiSettings = MapUiSettings(
                myLocationButtonEnabled = true,
                zoomControlsEnabled = true
            )
        ) {
            // 1. Magenta background (Nasi Lemak)
            AdvancedMarker(
                state = markerState1,
                title = "Warung Burung Hantu",
                pinConfig = PinConfig.builder()
                    .setBackgroundColor(AndroidColor.MAGENTA)
                    .build()
            )

            // 2. Blue border (Burger)
            AdvancedMarker(
                state = markerState2,
                title = "Abang Lan (Burger)",
                pinConfig = PinConfig.builder()
                    .setBorderColor(AndroidColor.BLUE)
                    .build()
            )

            // 3. Glyph Text "K" (Nasi Kerabu)
            AdvancedMarker(
                state = markerState3,
                title = "Moknab (Nasi Kerabu)",
                pinConfig = PinConfig.builder()
                    .setGlyph(PinConfig.Glyph("K"))
                    .build()
            )

            // 4. Standard Advanced Marker (Laksa)
            AdvancedMarker(
                state = markerState4,
                title = "3Brother (Laksa Utaqa)"
            )
        }

        // Back Button
        IconButton(
            onClick = { navController.navigate("home") },
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
                .align(Alignment.TopStart)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f), CircleShape)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }

        // Action Button
        Button(
            onClick = { moveToCurrentLocation() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        ) {
            Text("Locate Me")
        }
    }
}
