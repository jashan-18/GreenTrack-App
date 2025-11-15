package com.example.greentrack

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        // 🌱 Report Issue Activity
        view.findViewById<Button>(R.id.btnReportIssue)?.setOnClickListener {
            startActivity(Intent(requireContext(), ReportIssueActivity::class.java))
        }

        // 🛒 Marketplace Fragment
        view.findViewById<Button>(R.id.btnMarketplace)?.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(MarketplaceFragment())
        }

        // 👤 Profile Activity
        view.findViewById<Button>(R.id.btnProfile)?.setOnClickListener {
            startActivity(Intent(requireContext(), ProfileActivity::class.java))
        }

        // 🎓 Training Fragment
        view.findViewById<Button>(R.id.btnTraining)?.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(TrainingFragment())
        }

        // ♻ Compost Fragment
        view.findViewById<Button>(R.id.btnCompost)?.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(CompostFragment())
        }

        // 🚚 Collection Fragment
        view.findViewById<Button>(R.id.btnCollection)?.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(CollectionFragment())
        }

        // 🥇 Champion Fragment
        view.findViewById<Button>(R.id.btnChampion)?.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(ChampionFragment())
        }

        // 🗺 Facility Locator Fragment
        view.findViewById<Button>(R.id.btnFacility)?.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(FacilityLocatorFragment())
        }

        // 📊 Reports Fragment
        view.findViewById<Button>(R.id.btnReports)?.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(ReportsFragment())
        }

        // 💰 Rewards Fragment
        view.findViewById<Button>(R.id.btnRewards)?.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(RewardsFragment())
        }

        // 🔔 Notifications Fragment
        view.findViewById<Button>(R.id.btnNotifications)?.setOnClickListener {
            (activity as? MainActivity)?.replaceFragment(NotificationsFragment())
        }

        // ⚙ Settings Activity
        view.findViewById<Button>(R.id.btnSettings)?.setOnClickListener {
            startActivity(Intent(requireContext(), SettingsActivity::class.java))
        }

        // 🛍 Marketplace Detail Activity
        view.findViewById<Button>(R.id.btnMarketplaceDetail)?.setOnClickListener {
            startActivity(Intent(requireContext(), MarketplaceDetailActivity::class.java))
        }

        return view
    }
}
