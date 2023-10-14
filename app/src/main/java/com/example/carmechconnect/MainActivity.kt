package com.example.carmechconnect

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.carmechconnect.LocationDetails.viewModel
import com.example.carmechconnect.databinding.ActivityMainBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.ScaleBarOverlay
import org.osmdroid.views.overlay.compass.CompassOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import org.osmdroid.util.GeoPoint


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mapView: MapView
    //private lateinit var viewModel: viewModel

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
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

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

        // Observe the POI list and add markers to the map
        // Observe the POI list and add markers to the map
        val viewModel = ViewModelProvider(this).get(viewModel::class.java)

        viewModel.poiList.observe(this) { poiList ->
            for (poi in poiList) {
                Log.d("List of POI", "${poi.name} (${poi.latitude}, ${poi.longitude})")
                addMarkerAtLocation(poi.latitude, poi.longitude, poi.name, poi.address)
            }
        }

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
                val latitude = myLocation.latitude
                val longitude = myLocation.longitude
                mapView.controller.setCenter(myLocation)
                mapView.controller.setZoom(16)

                // Use latitude and longitude as needed
                Log.d("MyApp", "Latitude: $latitude, Longitude: $longitude")
            } else {
                Log.d("MyApp", "myLocation is null")
            }
        } else {
            Log.d("MyApp", "Location overlay not found or not enabled")
        }
    }

    // Function to add a marker to the map at a specific location
    // Function to add a marker to the map at a specific location with a title
    // Function to add a marker to the map at a specific location with a title and address
    private fun addMarkerAtLocation(latitude: Double, longitude: Double, title: String, address: String) {
        val location = GeoPoint(latitude, longitude)

        // Create a marker and set its position
        val marker = Marker(mapView)
        marker.position = location

        // Set the title and snippet (address) for the marker
        marker.title = title
        marker.snippet = address

        // Add the marker to the map's overlays
        mapView.overlays.add(marker)

        // Force the map to refresh to display the new marker
        mapView.invalidate()
    }

}