package com.example.tpsynthese.ui.map


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.example.tpsynthese.R
import com.example.tpsynthese.databinding.ActivityMapsBinding
import com.fondesa.kpermissions.allGranted
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.GoogleMap
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity :AppCompatActivity(),OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding


    private  val locationPermissionRequest by lazy {
        permissionsBuilder(
            android.Manifest.permission.ACCESS_FINE_LOCATION

        ).build()
    }
    private val args: MapsActivityArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val permissionStatus = locationPermissionRequest.checkStatus()
        if(permissionStatus.allGranted())
        {
            @SuppressLint("MissingPermission")
            mMap.isMyLocationEnabled= true
        }


        mMap.isTrafficEnabled = true

        val meteoMarkerOptions = MarkerOptions().position(args.position)
            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            .title("${args.position.latitude},${args.position.longitude}")

        mMap.addMarker(meteoMarkerOptions)

        val cameraPosition = CameraPosition.Builder().target(args.position).zoom(12f).build()

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}