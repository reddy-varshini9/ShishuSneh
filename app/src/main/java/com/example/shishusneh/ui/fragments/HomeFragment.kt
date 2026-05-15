package com.example.shishusneh.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.shishusneh.R
import com.google.android.material.card.MaterialCardView

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var tvGreeting: TextView? = null
    private var tvProfile: TextView? = null
    private var tvGrowth: TextView? = null
    private var tvVaccine: TextView? = null
    private var vaccineProgress: ProgressBar? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        tvGreeting = view.findViewById(R.id.tvGreeting)
        tvProfile = view.findViewById(R.id.tvHomeProfile)
        tvGrowth = view.findViewById(R.id.tvHomeGrowth)
        tvVaccine = view.findViewById(R.id.tvHomeVaccine)
        vaccineProgress = view.findViewById(R.id.vaccineProgress)

        // Spring animations on cards
        val slideUp = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
        val cards = listOf<View>(
            view.findViewById(R.id.cardProfile),
            view.findViewById(R.id.cardGrowth),
            view.findViewById(R.id.cardVaccine)
        )

        cards.forEachIndexed { index, card ->
            card.postDelayed({
                card.visibility = View.VISIBLE
                card.startAnimation(slideUp)
            }, index * 150L)
        }
    }

    override fun onResume() {
        super.onResume()

        val hour = java.util.Calendar.getInstance().get(java.util.Calendar.HOUR_OF_DAY)
        tvGreeting?.text = when {
            hour < 12 -> "Good Morning 🌤️"
            hour < 17 -> "Good Afternoon ☀️"
            else -> "Good Evening 🌙"
        }

        val profilePrefs = requireActivity().getSharedPreferences("profile", Context.MODE_PRIVATE)
        val growthPrefs = requireActivity().getSharedPreferences("growth", Context.MODE_PRIVATE)
        val vaccinePrefs = requireActivity().getSharedPreferences("vax", Context.MODE_PRIVATE)

        tvProfile?.text = profilePrefs.getString("data", "Not set")

        val w = growthPrefs.getString("w", "--")
        val h = growthPrefs.getString("h", "--")
        tvGrowth?.text = "⚖️ $w kg  |  📏 $h cm"

        val bcg = vaccinePrefs.getBoolean("bcg", false)
        val hep = vaccinePrefs.getBoolean("hep", false)
        val polio = vaccinePrefs.getBoolean("polio", false)
        val done = listOf(bcg, hep, polio).count { it }
        tvVaccine?.text = "$done / 3 vaccines completed"
        vaccineProgress?.progress = (done * 100) / 3
    }
}