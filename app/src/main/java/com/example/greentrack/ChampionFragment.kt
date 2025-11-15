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

class ChampionFragment : Fragment() {

    private lateinit var progressChampionImpact: ProgressBar
    private lateinit var tvChampionImpact: TextView
    private lateinit var btnContactChampion: Button
    private lateinit var btnViewReports: Button
    private lateinit var btnNominateChampion: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_champion, container, false)

        progressChampionImpact = view.findViewById(R.id.progressChampionImpact)
        tvChampionImpact = view.findViewById(R.id.tvChampionImpact)
        btnContactChampion = view.findViewById(R.id.btnContactChampion)
        btnViewReports = view.findViewById(R.id.btnViewReports)
        btnNominateChampion = view.findViewById(R.id.btnNominateChampion)

        // Random impact score
        val impact = Random.nextInt(60, 95)
        progressChampionImpact.progress = impact
        tvChampionImpact.text = "Impact Score: $impact%"

        // Actions
        btnContactChampion.setOnClickListener {
            val phoneIntent = Intent(Intent.ACTION_DIAL)
            phoneIntent.data = Uri.parse("tel:9876543210")
            startActivity(phoneIntent)
        }

        btnViewReports.setOnClickListener {
            openLink("https://www.greentrack.gov/champion/reports")
        }

        btnNominateChampion.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("champion@greentrack.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Nominate a Green Champion")
            }
            startActivity(Intent.createChooser(emailIntent, "Send Nomination"))
        }

        return view
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
