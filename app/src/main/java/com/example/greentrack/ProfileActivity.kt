package com.example.greentrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText

class ProfileActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var etPhone: TextInputEditText
    private lateinit var etRole: TextInputEditText
    private lateinit var tvEmail: TextView
    private lateinit var btnEditProfile: Button
    private lateinit var btnLogout: Button

    private var isEditing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // 🔹 Initialize views
        toolbar = findViewById(R.id.toolbarProfile)
        etPhone = findViewById(R.id.etPhone)
        etRole = findViewById(R.id.etRole)
        tvEmail = findViewById(R.id.tvEmail)
        btnEditProfile = findViewById(R.id.btnEditProfile)
        btnLogout = findViewById(R.id.btnLogout)

        // 🔹 Setup toolbar navigation
        toolbar.setNavigationOnClickListener {
            Toast.makeText(this, "Returning to previous screen", Toast.LENGTH_SHORT).show()
            finish()
        }

        // 🔹 Edit Profile Button
        btnEditProfile.setOnClickListener {
            if (!isEditing) {
                // Enable editing mode
                enableEditing(true)
                btnEditProfile.text = "Save Changes"
                btnEditProfile.setBackgroundResource(R.drawable.bg_gradient_button)
                Toast.makeText(this, "Edit mode enabled", Toast.LENGTH_SHORT).show()
            } else {
                // Save changes (for now just disable editing)
                enableEditing(false)
                btnEditProfile.text = "Edit Profile"
                btnEditProfile.setBackgroundResource(R.drawable.rounded_button)
                Toast.makeText(this, "Changes saved successfully", Toast.LENGTH_SHORT).show()
            }
            isEditing = !isEditing
        }

        // 🔹 Logout Button
        btnLogout.setOnClickListener {
            Toast.makeText(this, "Logged out successfully!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    // 🔹 Enable or disable edit mode
    private fun enableEditing(enable: Boolean) {
        etPhone.isEnabled = enable
        etRole.isEnabled = enable

        
    }
}
