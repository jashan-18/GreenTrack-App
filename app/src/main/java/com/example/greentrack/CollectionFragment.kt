package com.example.greentrack

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import kotlin.random.Random

class CollectionFragment : Fragment() {

    private lateinit var tvLastPickup: TextView
    private lateinit var tvNextPickup: TextView
    private lateinit var progressWaste: ProgressBar
    private lateinit var tvCollectionEfficiency: TextView
    private lateinit var btnTrackCollector: Button
    private lateinit var btnReportMissedPickup: Button
    private lateinit var btnViewSchedule: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_collection, container, false)

        tvLastPickup = view.findViewById(R.id.tvLastPickup)
        tvNextPickup = view.findViewById(R.id.tvNextPickup)
        progressWaste = view.findViewById(R.id.progressWaste)
        tvCollectionEfficiency = view.findViewById(R.id.tvCollectionEfficiency)
        btnTrackCollector = view.findViewById(R.id.btnTrackCollector)
        btnReportMissedPickup = view.findViewById(R.id.btnReportMissedPickup)
        btnViewSchedule = view.findViewById(R.id.btnViewSchedule)

        // Simulated data
        val efficiency = Random.nextInt(60, 95)
        progressWaste.progress = efficiency
        tvCollectionEfficiency.text = "Collection Efficiency: $efficiency%"

        // Button actions
        btnTrackCollector.setOnClickListener {
            openMapLocation("https://www.google.com/maps/search/waste+collector+near+me")
        }

        btnReportMissedPickup.setOnClickListener {
            sendMailToDepartment()
        }

        btnViewSchedule.setOnClickListener {
            openLink("https://www.wasteconnections.com/pickup-schedule/")
        }

        return view
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun openMapLocation(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    private fun sendMailToDepartment() {
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf("support@greentrack.com"))
            putExtra(Intent.EXTRA_SUBJECT, "Missed Waste Pickup Report")
        }
        startActivity(Intent.createChooser(emailIntent, "Send Report"))
    }
}
