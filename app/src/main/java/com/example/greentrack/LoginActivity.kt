package com.example.greentrack

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var nameInputLayout: TextInputLayout
    private lateinit var emailInputLayout: TextInputLayout
    private lateinit var phoneInputLayout: TextInputLayout
    private lateinit var otpInputLayout: TextInputLayout

    private lateinit var etName: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPhoneNumber: TextInputEditText
    private lateinit var etOTP: TextInputEditText
    private lateinit var btnLogin: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Views
        nameInputLayout = findViewById(R.id.nameInputLayout)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        phoneInputLayout = findViewById(R.id.phoneInputLayout)
        otpInputLayout = findViewById(R.id.otpInputLayout)

        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        etOTP = findViewById(R.id.etOTP)
        btnLogin = findViewById(R.id.btnLogin)
        progressBar = findViewById(R.id.progressBarLogin)

        btnLogin.setOnClickListener { validateInputs() }
    }

    private fun validateInputs() {
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val phone = etPhoneNumber.text.toString().trim()
        val otp = etOTP.text.toString().trim()

        var isValid = true

        // Validation checks
        if (name.isEmpty()) {
            nameInputLayout.error = "Enter your name"
            isValid = false
        } else nameInputLayout.error = null

        if (email.isEmpty()) {
            emailInputLayout.error = "Enter your email"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInputLayout.error = "Invalid email"
            isValid = false
        } else emailInputLayout.error = null

        if (phone.isEmpty()) {
            phoneInputLayout.error = "Enter your phone number"
            isValid = false
        } else if (phone.length != 10) {
            phoneInputLayout.error = "Must be 10 digits"
            isValid = false
        } else phoneInputLayout.error = null

        if (otp.isEmpty()) {
            otpInputLayout.error = "Enter OTP"
            isValid = false
        } else if (otp.length != 6) {
            otpInputLayout.error = "OTP must be 6 digits"
            isValid = false
        } else otpInputLayout.error = null

        if (isValid) {
            // ✅ Show progress bar
            btnLogin.isEnabled = false
            progressBar.visibility = android.view.View.VISIBLE

            // Simulate loading for 2 seconds before dashboard
            Handler(Looper.getMainLooper()).postDelayed({
                progressBar.visibility = android.view.View.GONE
                btnLogin.isEnabled = true
                Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 2000)
        }
    }
}
