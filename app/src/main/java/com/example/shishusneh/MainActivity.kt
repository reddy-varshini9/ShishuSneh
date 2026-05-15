package com.example.shishusneh

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.shishusneh.ui.fragments.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        load(HomeFragment())

        findViewById<android.view.View>(R.id.bottomHome).setOnClickListener {
            animateSwitch(HomeFragment())
        }

        findViewById<android.view.View>(R.id.bottomGrowth).setOnClickListener {
            animateSwitch(GrowthFragment())
        }

        findViewById<android.view.View>(R.id.bottomVaccine).setOnClickListener {
            animateSwitch(VaccineFragment())
        }

        findViewById<android.view.View>(R.id.bottomProfile).setOnClickListener {
            animateSwitch(ProfileFragment())
        }
    }

    private fun animateSwitch(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_up,
                android.R.anim.fade_out
            )
            .replace(R.id.frameContainer, fragment)
            .commit()
    }

    private fun load(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameContainer, fragment)
            .commit()
    }
}