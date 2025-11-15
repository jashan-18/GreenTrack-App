package com.example.greentrack

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class MarketplaceDetailActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var imgProduct: ImageView
    private lateinit var tvProductName: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var tvProductDescription: TextView
    private lateinit var btnContactSeller: Button
    private lateinit var btnBuyNow: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace_detail)

        toolbar = findViewById(R.id.toolbarMarketplaceDetail)
        imgProduct = findViewById(R.id.imgProduct)
        tvProductName = findViewById(R.id.tvProductName)
        tvProductPrice = findViewById(R.id.tvProductPrice)
        tvProductDescription = findViewById(R.id.tvProductDescription)
        btnContactSeller = findViewById(R.id.btnContactSeller)
        btnBuyNow = findViewById(R.id.btnBuyNow)

        // Back navigation
        toolbar.setNavigationOnClickListener { finish() }

        // Contact Seller button
        btnContactSeller.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:")
                putExtra(Intent.EXTRA_EMAIL, arrayOf("seller@greentrack.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Inquiry about ${tvProductName.text}")
            }
            try {
                startActivity(Intent.createChooser(emailIntent, "Contact Seller"))
            } catch (e: Exception) {
                Toast.makeText(this, "No email app found!", Toast.LENGTH_SHORT).show()
            }
        }

        // Buy Now button
        btnBuyNow.setOnClickListener {
            Toast.makeText(this, "Redirecting to payment page...", Toast.LENGTH_SHORT).show()
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.greentrack-marketplace.com"))
            startActivity(browserIntent)
        }
    }
}
