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

class CompostFragment : Fragment() {

    private lateinit var progressCompost: ProgressBar
    private lateinit var tvProgressPercent: TextView
    private lateinit var btnLearnCompost: Button
    private lateinit var btnWatchVideo: Button
    private lateinit var btnReadGuide: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_compost, container, false)

        progressCompost = view.findViewById(R.id.progressCompost)
        tvProgressPercent = view.findViewById(R.id.tvProgressPercent)
        btnLearnCompost = view.findViewById(R.id.btnLearnCompost)
        btnWatchVideo = view.findViewById(R.id.btnWatchVideo)
        btnReadGuide = view.findViewById(R.id.btnReadGuide)

        // Simulate compost progress update
        val progress = Random.nextInt(40, 90)
        progressCompost.progress = progress
        tvProgressPercent.text = "$progress% Complete"

        // Button actions
        btnLearnCompost.setOnClickListener {
            openLink("https://www.wikihow.com/Make-Compost")
        }

        btnWatchVideo.setOnClickListener {
            openLink("https://www.youtube.com/watch?v=egyNJ7xPyoQ&t=13s")
        }

        btnReadGuide.setOnClickListener {
            openLink("https://www.epa.gov/recycle/composting-home")
        }

        return view
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
