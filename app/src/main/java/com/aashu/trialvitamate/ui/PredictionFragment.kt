package com.aashu.trialvitamate.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.aashu.trialvitamate.R
import com.aashu.trialvitamate.databinding.FragmentPredictionBinding

class PredictionFragment : Fragment(R.layout.fragment_prediction) {

    private var _binding: FragmentPredictionBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentPredictionBinding.bind(view)

        binding.btnPredict.setOnClickListener {
            val glucose = binding.etGlucose.text.toString().toFloatOrNull() ?: 0f
            val bp = binding.etBP.text.toString().toFloatOrNull() ?: 0f
            val bmi = binding.etBMI.text.toString().toFloatOrNull() ?: 0f
            val age = binding.etAge.text.toString().toIntOrNull() ?: 0

            val risk = calculateRisk(glucose, bp, bmi, age)

            binding.tvResult.text = "Risk Level: $risk"
        }
    }

    private fun calculateRisk(g: Float, bp: Float, bmi: Float, age: Int): String {
        var score = 0

        if (g > 140) score += 2
        if (bp > 90) score += 2
        if (bmi > 30) score += 2
        if (age > 50) score += 1

        return when {
            score >= 5 -> "High Risk"
            score >= 3 -> "Medium Risk"
            else -> "Low Risk"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}