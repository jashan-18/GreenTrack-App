package com.example.greentrack

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

class ReportsFragment : Fragment() {

    private lateinit var tvDailyStats: TextView
    private lateinit var tvWeeklyStats: TextView
    private lateinit var tvMonthlyStats: TextView

    private lateinit var progressDaily: ProgressBar
    private lateinit var progressWeekly: ProgressBar
    private lateinit var progressMonthly: ProgressBar

    private lateinit var btnViewDaily: Button
    private lateinit var btnViewWeekly: Button
    private lateinit var btnViewMonthly: Button

    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_reports, container, false)

        // Initialize TextViews
        tvDailyStats = view.findViewById(R.id.tvDailyStats)
        tvWeeklyStats = view.findViewById(R.id.tvWeeklyStats)
        tvMonthlyStats = view.findViewById(R.id.tvMonthlyStats)

        // Initialize ProgressBars
        progressDaily = view.findViewById(R.id.progressDaily)
        progressWeekly = view.findViewById(R.id.progressWeekly)
        progressMonthly = view.findViewById(R.id.progressMonthly)

        // Initialize Buttons
        btnViewDaily = view.findViewById(R.id.btnViewDailyReport)
        btnViewWeekly = view.findViewById(R.id.btnViewWeeklyReport)
        btnViewMonthly = view.findViewById(R.id.btnViewMonthlyReport)

        // Load Firestore Data
        loadReportData("daily", tvDailyStats, progressDaily)
        loadReportData("weekly", tvWeeklyStats, progressWeekly)
        loadReportData("monthly", tvMonthlyStats, progressMonthly)

        // Button Click Listeners
        btnViewDaily.setOnClickListener { showZoomDialog("Daily Report", tvDailyStats.text.toString()) }
        btnViewWeekly.setOnClickListener { showZoomDialog("Weekly Report", tvWeeklyStats.text.toString()) }
        btnViewMonthly.setOnClickListener { showZoomDialog("Monthly Report", tvMonthlyStats.text.toString()) }

        return view
    }

    private fun loadReportData(docId: String, textView: TextView, progressBar: ProgressBar) {
        progressBar.visibility = View.VISIBLE
        firestore.collection("ecoImpact").document(docId)
            .get()
            .addOnSuccessListener { document ->
                progressBar.visibility = View.GONE
                if (document.exists()) {
                    val waste = document.getDouble("wasteCollected") ?: 0.0
                    val compost = document.getDouble("compostProduced") ?: 0.0
                    val carbon = document.getDouble("carbonSaved") ?: 0.0

                    textView.text = """
                        Waste Collected: $waste kg
                        Compost Produced: $compost kg
                        Carbon Saved: $carbon kg
                    """.trimIndent()
                } else {
                    textView.text = "No data found."
                }
            }
            .addOnFailureListener {
                progressBar.visibility = View.GONE
                textView.text = "Error loading data"
                Toast.makeText(requireContext(), "Failed to fetch $docId data", Toast.LENGTH_SHORT).show()
            }
    }

    /** 🔍 Zoom-in dialog for detailed report view */
    private fun showZoomDialog(title: String, details: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_zoom_details, null)

        val tvTitle = dialogView.findViewById<TextView>(R.id.tvDialogTitle)
        val tvDetails = dialogView.findViewById<TextView>(R.id.tvDialogDetails)

        tvTitle.text = title
        tvDetails.text = details

        // Small zoom animation
        dialogView.scaleX = 0.8f
        dialogView.scaleY = 0.8f
        ObjectAnimator.ofFloat(dialogView, "scaleX", 1f).setDuration(300).apply {
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }
        ObjectAnimator.ofFloat(dialogView, "scaleY", 1f).setDuration(300).apply {
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
            .show()
    }
}
