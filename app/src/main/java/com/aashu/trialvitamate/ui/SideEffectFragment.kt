package com.aashu.trialvitamate.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.aashu.trialvitamate.R
import com.aashu.trialvitamate.databinding.FragmentSideEffectBinding

class SideEffectFragment : Fragment(R.layout.fragment_side_effect) {

    private var _binding: FragmentSideEffectBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentSideEffectBinding.bind(view)

        binding.btnAnalyze.setOnClickListener {
            val input = binding.etSymptoms.text.toString()
            val result = analyzeSymptoms(input)
            binding.tvResult.text = result
        }
    }

    // 🔥 SMART MATCH FUNCTION
    private fun containsKeyword(input: String, keywords: List<String>): Boolean {
        return keywords.any { keyword ->
            input.contains(keyword)
        }
    }

    private fun analyzeSymptoms(inputRaw: String): String {

        // 🔥 STEP 1: Normalize input
        var input = inputRaw.lowercase()

        // Replace common human mistakes
        input = input
            .replace("cant", "cannot")
            .replace("can't", "cannot")
            .replace("breath", "breathe")
            .replace("[^a-z ]".toRegex(), " ")
            .replace("\\s+".toRegex(), " ")
            .trim()

        // Split symptoms
        val symptoms = input.split(",", " and ").map { it.trim() }

        val filteredSymptoms = symptoms.filter {
            it.split(" ").size >= 2
        }

        if (filteredSymptoms.isEmpty()) {
            return "Please enter more detailed symptoms (e.g., chest pain, breathing problem)"
        }

        // 🔥 STEP 2: Define Keywords

        val severeKeywords = listOf(
            "heart attack", "cardiac", "chest pain",
            "cannot breathe", "shortness of breathe",
            "breathing problem", "breathe issue",
            "unconscious", "seizure", "stroke",
            "bleeding", "collapse", "paralysis"
        )

        val moderateKeywords = listOf(
            "fever", "vomiting", "dizziness", "headache",
            "pain", "infection", "swelling", "fatigue",
            "cough", "cold", "nausea"
        )

        val mildKeywords = listOf(
            "minor pain", "small cut", "irritation",
            "tooth pain", "gum pain"
        )

        // 🔥 STEP 3: Body Part Intelligence

        val severeBodyParts = listOf("heart", "chest", "brain", "lungs")
        val moderateBodyParts = listOf("stomach", "back", "head", "neck")
        val mildBodyParts = listOf("finger", "toe", "foot", "hand", "teeth", "gum")

        var score = 0
        var isSevere = false

        for (symptom in filteredSymptoms) {

            when {
                containsKeyword(symptom, severeKeywords) -> {
                    score += 5
                    isSevere = true
                }

                containsKeyword(symptom, moderateKeywords) -> {
                    score += 3
                }

                containsKeyword(symptom, mildKeywords) -> {
                    score += 1
                }

                containsKeyword(symptom, severeBodyParts) -> {
                    score += 4
                    isSevere = true
                }

                containsKeyword(symptom, moderateBodyParts) -> {
                    score += 2
                }

                containsKeyword(symptom, mildBodyParts) -> {
                    score += 1
                }

                else -> {
                    score += 1
                }
            }
        }

        // 🔥 STEP 4: Final Decision

        return when {
            isSevere || score >= 6 ->
                "⚠️ HIGH RISK!\nConsult doctor immediately."

            score >= 3 ->
                "Moderate symptoms.\nConsult doctor soon."

            else ->
                "Mild symptoms.\nRest and monitor."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}