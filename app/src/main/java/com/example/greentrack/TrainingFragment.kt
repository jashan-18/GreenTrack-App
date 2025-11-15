package com.example.greentrack

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import android.widget.Toast

class TrainingFragment : Fragment() {

    private lateinit var btnSegregationLearnMore: Button
    private lateinit var btnCompostingLearnMore: Button
    private lateinit var btnRecyclingLearnMore: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_training, container, false)

        btnSegregationLearnMore = view.findViewById(R.id.btnSegregationLearnMore)
        btnCompostingLearnMore = view.findViewById(R.id.btnCompostingLearnMore)
        btnRecyclingLearnMore = view.findViewById(R.id.btnRecyclingLearnMore)

        // Open YouTube video or resource link
        btnSegregationLearnMore.setOnClickListener {
            openLink("https://www.youtube.com/watch?v=0ZiD_Lb3Tm0")
        }

        btnCompostingLearnMore.setOnClickListener {
            openLink("https://www.wikihow.com/Make-Compost")
        }

        btnRecyclingLearnMore.setOnClickListener {
            openLink("https://www.unep.org/interactive/recycling/")
        }

        Toast.makeText(requireContext(), "Training content loaded", Toast.LENGTH_SHORT).show()
        return view
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}
