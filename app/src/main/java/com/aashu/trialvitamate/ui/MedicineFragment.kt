package com.aashu.trialvitamate.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aashu.trialvitamate.R
import com.aashu.trialvitamate.databinding.FragmentMedicineBinding
import com.aashu.trialvitamate.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedicineFragment : Fragment(R.layout.fragment_medicine) {

    private var _binding: FragmentMedicineBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentMedicineBinding.bind(view)

        binding.btnSearch.setOnClickListener {
            val medicineName = binding.etSearch.text.toString()

            if (medicineName.isEmpty()) {
                Toast.makeText(requireContext(), "Enter medicine name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            searchMedicine(medicineName)
        }
    }

    private fun searchMedicine(name: String) {
        RetrofitClient.instance.searchMedicine(name)
            .enqueue(object : Callback<com.aashu.trialvitamate.network.MedicineResponse> {

                override fun onResponse(
                    call: Call<com.aashu.trialvitamate.network.MedicineResponse>,
                    response: Response<com.aashu.trialvitamate.network.MedicineResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()

                        if (!data?.results.isNullOrEmpty()) {
                            val medicine = data!!.results[0]

                            val resultText = """
                                Purpose: ${medicine.purpose?.getOrNull(0)}

                                Warnings: ${medicine.warnings?.getOrNull(0)}

                                Side Effects: ${medicine.adverse_reactions?.getOrNull(0)}
                            """.trimIndent()

                            binding.tvResult.text = resultText
                        } else {
                            binding.tvResult.text = "No data found"
                        }
                    } else {
                        if (response.code() == 404) {
                            binding.tvResult.text = "Medicine not found. Try generic name (e.g., paracetamol)"
                        } else {
                            binding.tvResult.text = "Error: ${response.code()}"
                        }
                    }
                }

                override fun onFailure(
                    call: Call<com.aashu.trialvitamate.network.MedicineResponse>,
                    t: Throwable
                ) {
                    binding.tvResult.text = "Failed: ${t.message}"
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}