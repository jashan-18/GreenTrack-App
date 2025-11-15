package com.example.greentrack

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.logo)
        val appName = findViewById<TextView>(R.id.appName)
        val tagline = findViewById<TextView>(R.id.tagline)

        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        logo.startAnimation(fadeIn)
        appName.startAnimation(fadeIn)
        tagline.startAnimation(fadeIn)

        // Delay 2.5 seconds then open LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }, 2500)
    }
}
