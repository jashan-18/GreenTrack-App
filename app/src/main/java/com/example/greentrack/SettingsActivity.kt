package com.example.greentrack

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class SettingsActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var switchNotifications: SwitchMaterial
    private lateinit var switchDarkMode: SwitchMaterial
    private lateinit var tilLanguage: TextInputLayout
    private lateinit var etLanguage: MaterialAutoCompleteTextView
    private lateinit var btnAbout: Button
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        // 🔹 Load saved theme before UI draws
        prefs = getSharedPreferences("settings", MODE_PRIVATE)
        val darkModeEnabled = prefs.getBoolean("dark_mode", false)
        AppCompatDelegate.setDefaultNightMode(
            if (darkModeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize views
        toolbar = findViewById(R.id.toolbarSettings)
        switchNotifications = findViewById(R.id.switchNotifications)
        switchDarkMode = findViewById(R.id.switchDarkMode)
        tilLanguage = findViewById(R.id.tilLanguage)
        etLanguage = findViewById(R.id.etLanguage)
        btnAbout = findViewById(R.id.btnAbout)

        // Toolbar back navigation
        toolbar.setNavigationOnClickListener { finish() }

        // ✅ Set switch state from saved preference
        switchDarkMode.isChecked = darkModeEnabled

        // 🔹 Language Dropdown
        val languages = listOf("English", "Hindi", "Punjabi", "Tamil", "Bengali")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, languages)
        etLanguage.setAdapter(adapter)

        etLanguage.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(this, "Language set to ${languages[position]}", Toast.LENGTH_SHORT).show()
        }

        // 🔹 Notifications switch
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            Toast.makeText(
                this,
                if (isChecked) "Notifications Enabled" else "Notifications Disabled",
                Toast.LENGTH_SHORT
            ).show()
        }

        // 🌙 Real Dark Mode toggle
        switchDarkMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("dark_mode", isChecked).apply()
            AppCompatDelegate.setDefaultNightMode(
                if (isChecked) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            )

            Toast.makeText(
                this,
                if (isChecked) "Dark Mode Activated 🌙" else "Light Mode Activated ☀️",
                Toast.LENGTH_SHORT
            ).show()
        }

        // ℹ️ About App
        btnAbout.setOnClickListener {
            Toast.makeText(
                this,
                "GreenTrack - A Sustainable Waste Management App 🌿",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
