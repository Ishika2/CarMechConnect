package com.example.carmechconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.carmechconnect.databinding.ActivityMainBinding
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.util.GeoPoint

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView

    private val locationPermission = Manifest.permission.ACCESS_FINE_LOCATION
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1
    // Define keywords
    val keywords = listOf("mechanic", "service center")


    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            addLocationOverlay()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize OSMDroid configuration
        Configuration.getInstance().load(applicationContext, androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext))

        // Set up the map view
        mapView = binding.mapView
        mapView.setTileSource(TileSourceFactory.MAPNIK)
        mapView.setBuiltInZoomControls(true)
        mapView.setMultiTouchControls(true)

        // Add scale bar overlay
        val scaleBarOverlay = ScaleBarOverlay(mapView)
        mapView.overlays.add(scaleBarOverlay)

        // Add compass overlay
        val compassOverlay = CompassOverlay(this, mapView)
        compassOverlay.enableCompass()
        mapView.overlays.add(compassOverlay)

        binding.centerLocationButton.setOnClickListener {
            centerMapOnCurrentLocation()
        }

        checkLocationPermission()


    }

    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                locationPermission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            addLocationOverlay()
        } else {
            requestPermissionLauncher.launch(locationPermission)
        }
    }

    private fun addLocationOverlay() {
        val locationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(applicationContext), mapView)
        locationOverlay.enableMyLocation()
        mapView.overlays.add(locationOverlay)
    }

    private fun centerMapOnCurrentLocation() {
        val locationOverlay = mapView.overlays.firstOrNull { it is MyLocationNewOverlay } as MyLocationNewOverlay?
        if (locationOverlay != null && locationOverlay.isEnabled) {
            val myLocation = locationOverlay.myLocation
            if (myLocation != null) {
                mapView.controller.setCenter(myLocation)
                mapView.controller.setZoom(16)
                // Add log statements for debugging
                Log.d("MyApp", "Centered on current location: $myLocation")
            } else {
                Log.d("MyApp", "myLocation is null")
            }
        } else {
            Log.d("MyApp", "Location overlay not found or not enabled")
        }
    }
    private fun searchLocations(keywords: List<String>) {
        // Iterate through the keywords
        for (keyword in keywords) {
            // Implement your search logic here using your OSM data
            // For example, you can search for locations with specific tags like "amenity=restaurant" or "shop=mechanic"

            // Once you find matching locations, create GeoPoints for them
            val matchingLocations = findMatchingLocations(keyword)

            // Display the matching locations on the map
            displayLocationsOnMap(matchingLocations)
        }
    }

    private fun findMatchingLocations(keyword: String): List<org.osmdroid.util.GeoPoint> {
        // Implement your search logic here
        // You can parse your OSM data and find locations matching the keyword
        // Return a list of GeoPoints for matching locations
        // Each GeoPoint represents a location on the map
        return listOf(/* List of GeoPoints for matching locations */)
    }

    private fun displayLocationsOnMap(locations: List<org.osmdroid.util.GeoPoint>) {
        // Iterate through the list of GeoPoints and add markers to the map
        for (location in locations) {
            val marker = Marker(mapView)
            marker.position = location
            mapView.overlays.add(marker)
        }
    }
}