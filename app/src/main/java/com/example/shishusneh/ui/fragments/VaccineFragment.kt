package com.example.shishusneh.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.example.shishusneh.R

class VaccineFragment : Fragment(R.layout.fragment_vaccine) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val cb1 = view.findViewById<CheckBox>(R.id.cb1)
        val cb2 = view.findViewById<CheckBox>(R.id.cb2)
        val cb3 = view.findViewById<CheckBox>(R.id.cb3)

        val prefs = requireActivity().getSharedPreferences("vax", Context.MODE_PRIVATE)

        cb1.isChecked = prefs.getBoolean("bcg", false)
        cb2.isChecked = prefs.getBoolean("hep", false)
        cb3.isChecked = prefs.getBoolean("polio", false)

        cb1.setOnCheckedChangeListener { _, v -> prefs.edit().putBoolean("bcg", v).apply() }
        cb2.setOnCheckedChangeListener { _, v -> prefs.edit().putBoolean("hep", v).apply() }
        cb3.setOnCheckedChangeListener { _, v -> prefs.edit().putBoolean("polio", v).apply() }
    }
}