package com.aashu.trialvitamate.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.aashu.trialvitamate.R
import com.aashu.trialvitamate.databinding.FragmentAvoidBinding
import org.json.JSONObject

class AvoidFragment : Fragment(R.layout.fragment_avoid) {

    private var _binding: FragmentAvoidBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAvoidBinding.bind(view)

        binding.btnCheck.setOnClickListener {
            val input = binding.etInput.text.toString().lowercase()
            loadAvoidData(input)
        }
    }

    private fun loadAvoidData(key: String) {
        try {
            val inputStream = requireContext().assets.open("avoid_data.json")
            val json = inputStream.bufferedReader().use { it.readText() }

            val jsonObject = JSONObject(json)

            if (jsonObject.has(key)) {
                val array = jsonObject.getJSONArray(key)

                binding.tvResult.text = (0 until array.length())
                    .joinToString("\n") { "- ${array.getString(it)}" }

            } else {
                binding.tvResult.text = "No data found. Try: diabetes, paracetamol"
            }

        } catch (e: Exception) {
            binding.tvResult.text = "Error loading data"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}