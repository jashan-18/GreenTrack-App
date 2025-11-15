package com.example.greentrack

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class FacilityLocatorFragment : Fragment() {

    private lateinit var btnRecyclingDirections: Button
    private lateinit var btnCompostDirections: Button
    private lateinit var btnEnergyDirections: Button
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val REQUEST_LOCATION_PERMISSION = 200

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_facility_locator, container, false)

        btnRecyclingDirections = view.findViewById(R.id.btnRecyclingDirections)
        btnCompostDirections = view.findViewById(R.id.btnCompostDirections)
        btnEnergyDirections = view.findViewById(R.id.btnEnergyDirections)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        // Handle button clicks
        btnRecyclingDirections.setOnClickListener {
            openNearbySearch("recycling center")
        }

        btnCompostDirections.setOnClickListener {
            openNearbySearch("compost unit")
        }

        btnEnergyDirections.setOnClickListener {
            openNearbySearch("waste to energy plant")
        }

        return view
    }

    private fun openNearbySearch(query: String) {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val lat = location.latitude
                    val lng = location.longitude
                    val mapsUri =
                        Uri.parse("geo:$lat,$lng?q=${Uri.encode(query)} near me")

                    val mapIntent = Intent(Intent.ACTION_VIEW, mapsUri)
                    mapIntent.setPackage("com.google.android.apps.maps")

                    try {
                        startActivity(mapIntent)
                    } catch (e: Exception) {
                        Toast.makeText(requireContext(), "Google Maps not installed", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Unable to get current location", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Request location permission if not granted
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    // Handle permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Permission granted. Try again.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Location permission denied.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
