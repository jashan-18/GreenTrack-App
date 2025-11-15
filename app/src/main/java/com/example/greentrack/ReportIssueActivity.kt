package com.example.greentrack

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.SmsManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.appbar.MaterialToolbar
import java.text.SimpleDateFormat
import java.util.*

class ReportIssueActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var btnCapture: Button
    private lateinit var btnSubmit: Button
    private lateinit var imgPreview: ImageView
    private lateinit var tvLocation: TextView
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val REQUEST_LOCATION_PERMISSION = 2
    private val REQUEST_SEND_SMS_PERMISSION = 102

    private var currentLocation: Location? = null
    private var lastCapturedBitmap: Bitmap? = null

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val imageBitmap = result.data?.extras?.get("data") as? Bitmap
                if (imageBitmap != null) {
                    lastCapturedBitmap = imageBitmap
                    imgPreview.setImageBitmap(imageBitmap)
                } else {
                    Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_issue)

        toolbar = findViewById(R.id.toolbarReport)
        btnCapture = findViewById(R.id.btnCapture)
        btnSubmit = findViewById(R.id.btnSubmit)
        imgPreview = findViewById(R.id.imgPreview)
        tvLocation = findViewById(R.id.tvLocation)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        toolbar.setNavigationOnClickListener { finish() }
        checkAndRequestPermissions()

        btnCapture.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraLauncher.launch(cameraIntent)
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    101
                )
            }
        }

        btnSubmit.setOnClickListener {
            // perform checks, then send
            sendReportAsSmsOrIntent("9053616214")
        }

        getCurrentLocation()
    }

    private fun checkAndRequestPermissions() {
        val permissionsNeeded = mutableListOf<String>()
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }
        // Note: don't auto-request SEND_SMS here; we request when needed to avoid scaring user
        if (permissionsNeeded.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsNeeded.toTypedArray(), 100)
        }
    }

    // Get location
    private fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        } else {
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    currentLocation = location
                    tvLocation.text =
                        "Location: Lat: ${location.latitude}, Lng: ${location.longitude}"
                } else {
                    tvLocation.text = "Location: Unable to detect"
                }
            }
        }
    }

    // Core: try programmatic SMS, else fallback to SMS intent
    private fun sendReportAsSmsOrIntent(phoneNumber: String) {
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(Date())
        val locationText = if (currentLocation != null) {
            "Lat: ${currentLocation!!.latitude}, Lng: ${currentLocation!!.longitude}"
        } else {
            "Location: Unknown"
        }

        // Compose message
        val message = StringBuilder()
            .append("GreenTrack - Waste Report\n")
            .append("Time: $timestamp\n")
            .append("Location: $locationText\n")
            .append("Note: Photo captured in app (not attached via SMS).")
            .toString()

        // If app has SEND_SMS permission, send programmatically
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
            == PackageManager.PERMISSION_GRANTED
        ) {
            try {
                val smsManager = SmsManager.getDefault()
                // For long messages use divideMessage
                val parts = smsManager.divideMessage(message)
                smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null)
                Toast.makeText(this, "Report sent via SMS", Toast.LENGTH_LONG).show()
                finish()
            } catch (ex: Exception) {
                ex.printStackTrace()
                Toast.makeText(this, "Failed to send SMS: ${ex.message}", Toast.LENGTH_LONG).show()
            }
        } else {
            // Request SEND_SMS permission – if granted, user should click submit again
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.SEND_SMS),
                REQUEST_SEND_SMS_PERMISSION
            )

            // Immediately open SMS app as fallback (pre-filled) so user can send manually
            openSmsAppFallback(phoneNumber, message)
        }
    }

    // Fallback: open native messaging app with prefilled body (no permission required)
    private fun openSmsAppFallback(phoneNumber: String, message: String) {
        try {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("smsto:$phoneNumber") // ensures only SMS apps handle this
                putExtra("sms_body", message)
            }
            startActivity(intent)
        } catch (ex: Exception) {
            Toast.makeText(this, "No SMS app found on device", Toast.LENGTH_SHORT).show()
        }
    }

    // Handle permission results
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_SEND_SMS_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted — inform user to tap Submit again to send
                Toast.makeText(this, "SEND_SMS permission granted. Tap Submit again to send SMS.", Toast.LENGTH_LONG).show()
            } else {
                // Permission denied — we already opened fallback SMS Intent earlier
                Toast.makeText(this, "SEND_SMS permission denied. Using Messages app fallback.", Toast.LENGTH_LONG).show()
            }
        }

        // existing handling for other permission codes (100, 101, etc.)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Permissions granted!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Camera/Location permission denied!", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == 101) { // camera
            // nothing special — user can try capture again
        } else if (requestCode == REQUEST_LOCATION_PERMISSION) {
            // request handled; try to get location again
            getCurrentLocation()
        }
    }
}
