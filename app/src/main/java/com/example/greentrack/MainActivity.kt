package com.example.greentrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var fabReport: FloatingActionButton
    private var backPressedTime = 0L
    private lateinit var backToast: Toast

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this) // ✅ Ensure Firestore works
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.topAppBar)
        fabReport = findViewById(R.id.fabReport)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        backToast = Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT)

        if (savedInstanceState == null) {
            replaceFragment(DashboardFragment(), "Dashboard", addToBackStack = false)
        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_dashboard -> replaceFragment(DashboardFragment(), "Dashboard")
                R.id.menu_training -> replaceFragment(TrainingFragment(), "Training")
                R.id.menu_reports -> replaceFragment(ReportsFragment(), "Reports")
                R.id.menu_facility -> replaceFragment(FacilityLocatorFragment(), "Facility Locator")
                R.id.menu_marketplace -> replaceFragment(MarketplaceFragment(), "Marketplace")
                R.id.menu_champion -> replaceFragment(ChampionFragment(), "Champion")
                R.id.menu_profile -> startActivity(Intent(this, ProfileActivity::class.java))
                R.id.menu_settings -> startActivity(Intent(this, SettingsActivity::class.java))
            }
            false
        }

        fabReport.setOnClickListener {
            startActivity(Intent(this, ReportIssueActivity::class.java))
        }

        // ✅ Handle Back Press
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val count = supportFragmentManager.backStackEntryCount
                if (count > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    if (System.currentTimeMillis() - backPressedTime < 2000) {
                        backToast.cancel()
                        finishAffinity()
                    } else {
                        backToast.show()
                        backPressedTime = System.currentTimeMillis()
                    }
                }
            }
        })
    }

    fun replaceFragment(fragment: Fragment, title: String = "", addToBackStack: Boolean = true) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(
            android.R.anim.fade_in,
            android.R.anim.fade_out,
            android.R.anim.fade_in,
            android.R.anim.fade_out
        )
        transaction.replace(R.id.fragmentContainer, fragment)
        if (addToBackStack) {
            transaction.addToBackStack(fragment::class.java.simpleName)
        }
        transaction.commit()

        // 🏷️ Optional: Set Toolbar Title
        toolbar.title = title

        // 👀 Control FAB visibility
        fabReport.visibility = when (fragment) {
            is DashboardFragment, is ReportsFragment -> View.VISIBLE
            else -> View.GONE
        }
    }
}
