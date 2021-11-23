package com.example.myphotos.ui.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myphotos.R
import com.example.myphotos.databinding.MapFragmentBinding
import com.example.myphotos.ui.image.ImageViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.core.graphics.drawable.DrawableCompat

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color

import androidx.core.content.res.ResourcesCompat

import android.graphics.drawable.Drawable

import androidx.annotation.ColorInt

import androidx.annotation.DrawableRes

import com.google.android.gms.maps.model.BitmapDescriptor





class MapFragment : Fragment(), OnMapReadyCallback {

    val imageViewModel: ImageViewModel by viewModel()
    private lateinit var mMap: GoogleMap
    var binding: MapFragmentBinding? = null
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var myLocation: LatLng? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MapFragmentBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        binding!!.mapView.onCreate(savedInstanceState)
        binding!!.mapView.getMapAsync(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                myLocation = LatLng(p0.lastLocation.latitude, p0.lastLocation.longitude)
                mMap.addMarker(MarkerOptions().position(myLocation!!).title("My location"))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 16f))
            }
        }

        binding!!.btnMap.setOnClickListener {
            if (context?.applicationContext?.let {
                    ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                } != PackageManager.PERMISSION_GRANTED
            ) {
                activity?.let {
                    ActivityCompat.requestPermissions(
                        it, arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_NETWORK_STATE
                        ), 2
                    )
                }
            } else {
                locationWizardry()
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationWizardry()
    }

    override fun onResume() {
        super.onResume()
        binding!!.mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding!!.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding!!.mapView.onDestroy()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // добовляем маркер с цветом
        mMap.addMarker(
            MarkerOptions().position(LatLng(11.0, 10.0))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            )

        // добовляем маркер с картинкой
        val icon = vectorToBitmap(R.drawable.ic_baseline_airplane_ticket_24,resources.getColor(R.color.cardview_dark_background))
        mMap.addMarker(
            MarkerOptions().position(LatLng(10.0, 10.0))
                .title("gggggggg")
               .icon(icon)
        )
        // добовляем маркеры
        val icons = vectorToBitmap(R.drawable.ic_baseline_photo_24,resources.getColor(R.color.cardview_dark_background))
        imageViewModel.photoListLiveData.observe(this.viewLifecycleOwner, {

            it.forEach {

                val location = LatLng(it.lat, it.lng)
                mMap!!.addMarker(MarkerOptions().position( location)
                    .title("${it.url}")
                    .icon(icons)
                )
                mMap.moveCamera(CameraUpdateFactory.newLatLng(location))

            }
        })

        mMap.uiSettings.isZoomControlsEnabled = true

    }

    @SuppressLint("MissingPermission")
    private fun locationWizardry() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        //Initially, get last known location. We can refine this estimate later
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                myLocation = LatLng(location.latitude, location.longitude)
            }
        }

        //now for receiving constant location updates:
        createLocRequest()
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(activity)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                try {
                    e.startResolutionForResult(activity, 500)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
        //actually start listening for updates: See on Resume(). It's done there so that conveniently we can stop listening in onPause
    }


    private fun createLocRequest() {
        locationRequest = LocationRequest.create()
        locationRequest.interval = 100000
        locationRequest.fastestInterval = 10000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun vectorToBitmap(@DrawableRes id: Int, @ColorInt color: Int): BitmapDescriptor? {
        val vectorDrawable = ResourcesCompat.getDrawable(resources, id, null)
        val bitmap = Bitmap.createBitmap(
            vectorDrawable!!.intrinsicWidth,
            vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        DrawableCompat.setTint(vectorDrawable, color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}

