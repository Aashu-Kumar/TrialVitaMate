package com.aashu.trialvitamate.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.aashu.trialvitamate.R
import com.aashu.trialvitamate.databinding.FragmentDietBinding
import org.json.JSONObject

class DietFragment : Fragment(R.layout.fragment_diet) {

    private var _binding: FragmentDietBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDietBinding.bind(view)

        binding.btnLoad.setOnClickListener {
            val disease = binding.etDisease.text.toString().lowercase()
            loadData(disease)
        }
    }

    private fun loadData(disease: String) {
        try {
            val inputStream = requireContext().assets.open("diet_data.json")
            val json = inputStream.bufferedReader().use { it.readText() }

            val jsonObject = JSONObject(json)

            if (jsonObject.has(disease)) {
                val data = jsonObject.getJSONObject(disease)

                val diet = data.getJSONArray("diet")
                val exercise = data.getJSONArray("exercise")

                binding.tvDiet.text = "Diet:\n" + (0 until diet.length())
                    .joinToString("\n") { "- ${diet.getString(it)}" }

                binding.tvExercise.text = "Exercise:\n" + (0 until exercise.length())
                    .joinToString("\n") { "- ${exercise.getString(it)}" }

            } else {
                binding.tvDiet.text = "No data found"
                binding.tvExercise.text = ""
            }

        } catch (e: Exception) {
            binding.tvDiet.text = "Error loading data"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}