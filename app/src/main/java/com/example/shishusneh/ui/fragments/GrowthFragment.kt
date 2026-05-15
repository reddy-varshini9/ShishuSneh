package com.example.shishusneh.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.shishusneh.R
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class GrowthFragment : Fragment(R.layout.fragment_growth) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val etWeight = view.findViewById<TextInputEditText>(R.id.etWeight)
        val etHeight = view.findViewById<TextInputEditText>(R.id.etHeight)
        val btn = view.findViewById<Button>(R.id.btnGrowthSave)
        val tv = view.findViewById<TextView>(R.id.tvGrowthData)
        val historyLayout = view.findViewById<LinearLayout>(R.id.llHistory)

        val prefs = requireActivity().getSharedPreferences("growth", Context.MODE_PRIVATE)

        loadHistory(prefs, historyLayout, tv)

        btn.setOnClickListener {
            val w = etWeight.text.toString()
            val h = etHeight.text.toString()

            if (w.isEmpty() || h.isEmpty()) {
                if (w.isEmpty()) etWeight.error = "Enter weight"
                if (h.isEmpty()) etHeight.error = "Enter height"
                return@setOnClickListener
            }

            val date = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
            val historyJson = prefs.getString("history", "[]")
            val array = JSONArray(historyJson)

            val entry = JSONObject()
            entry.put("w", w)
            entry.put("h", h)
            entry.put("date", date)
            array.put(entry)

            // Keep only last 3
            val trimmed = JSONArray()
            val start = if (array.length() > 3) array.length() - 3 else 0
            for (i in start until array.length()) trimmed.put(array.get(i))

            prefs.edit()
                .putString("w", w)
                .putString("h", h)
                .putString("history", trimmed.toString())
                .apply()

            etWeight.text?.clear()
            etHeight.text?.clear()
            loadHistory(prefs, historyLayout, tv)
        }
    }

    private fun loadHistory(
        prefs: android.content.SharedPreferences,
        historyLayout: LinearLayout,
        tv: TextView
    ) {
        val w = prefs.getString("w", "--")
        val h = prefs.getString("h", "--")
        tv.text = "⚖️ $w kg  |  📏 $h cm"

        historyLayout.removeAllViews()
        val historyJson = prefs.getString("history", "[]")
        val array = JSONArray(historyJson)

        for (i in array.length() - 1 downTo 0) {
            val entry = array.getJSONObject(i)
            val entryView = TextView(requireContext())
            entryView.text = "📅 ${entry.getString("date")}  —  ⚖️ ${entry.getString("w")} kg  |  📏 ${entry.getString("h")} cm"
            entryView.textSize = 14f
            entryView.setTextColor(android.graphics.Color.parseColor("#B0B7C3"))
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.topMargin = 10
            entryView.layoutParams = params
            historyLayout.addView(entryView)
        }
    }
}