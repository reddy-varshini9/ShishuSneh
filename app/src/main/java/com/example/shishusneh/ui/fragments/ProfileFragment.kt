package com.example.shishusneh.ui.fragments

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.shishusneh.R
import java.util.Calendar

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var selectedDob: String = ""
    private var selectedAgeMonths: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val name = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.etBabyName)
        val btn = view.findViewById<Button>(R.id.btnSave)
        val tv = view.findViewById<TextView>(R.id.tvSavedData)
        val dobBtn = view.findViewById<Button>(R.id.btnPickDob)
        val tvDob = view.findViewById<TextView>(R.id.tvDobResult)

        val prefs = requireActivity().getSharedPreferences("profile", Context.MODE_PRIVATE)

        tv.text = prefs.getString("data", "No Data")
        tvDob.text = prefs.getString("dob_display", "No DOB set")

        dobBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(requireContext(), { _, year, month, day ->
                val today = Calendar.getInstance()
                val ageMonths = (today.get(Calendar.YEAR) - year) * 12 +
                        (today.get(Calendar.MONTH) - month)
                selectedAgeMonths = ageMonths
                selectedDob = "$day/${month + 1}/$year"
                tvDob.text = "DOB: $selectedDob  |  Age: $ageMonths months"
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        btn.setOnClickListener {
            val babyName = name.text.toString()
            if (babyName.isEmpty()) {
                name.error = "Enter baby name"
                return@setOnClickListener
            }
            val data = "👶 $babyName  |  📅 $selectedAgeMonths months"
            prefs.edit()
                .putString("data", data)
                .putString("dob_display", tvDob.text.toString())
                .apply()
            tv.text = data
        }
    }
}