package com.cascer.dicodingstoryapp.ui.story.maps

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cascer.dicodingstoryapp.R
import com.cascer.dicodingstoryapp.databinding.ActivityStoryMapsBinding
import com.cascer.dicodingstoryapp.ui.story.StoryViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityStoryMapsBinding
    private val viewModel: StoryViewModel by viewModels()
    private val boundsBuilder = LatLngBounds.Builder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStoryMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        viewModel.requestStoriesWithLocation()

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        setupViewModel()
    }

    private fun setupViewModel() {
        with(viewModel) {
            storiesWithLocation.observe(this@StoryMapsActivity) {
                it.forEach { item ->
                    val latLng = LatLng(item.lat.toDouble(), item.lon.toDouble())
                    mMap.addMarker(MarkerOptions().position(latLng).title(item.name))
                    boundsBuilder.include(latLng)
                }.also {
                    val bounds: LatLngBounds = boundsBuilder.build()
                    mMap.animateCamera(
                        CameraUpdateFactory.newLatLngBounds(
                            bounds,
                            resources.displayMetrics.widthPixels,
                            resources.displayMetrics.heightPixels,
                            300
                        )
                    )
                }
            }
        }
    }
}