package com.example.greentrack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class NotificationsFragment : Fragment() {

    private lateinit var btnClearNotifications: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        btnClearNotifications = view.findViewById(R.id.btnClearNotifications)

        btnClearNotifications.setOnClickListener {
            Toast.makeText(requireContext(), "All notifications cleared!", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
