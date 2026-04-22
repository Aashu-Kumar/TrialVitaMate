package com.aashu.trialvitamate.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.aashu.trialvitamate.R
import com.aashu.trialvitamate.databinding.FragmentConsultBinding
import java.text.SimpleDateFormat
import java.util.*

class ConsultFragment : Fragment(R.layout.fragment_consult) {

    private var _binding: FragmentConsultBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentConsultBinding.bind(view)

        binding.btnBook.setOnClickListener {

            val name = binding.etName.text.toString().trim()
            val problem = binding.etProblem.text.toString().trim()

            if (name.isEmpty() || problem.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 📅 Generate current date/time
            val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())
            val currentTime = sdf.format(Date())

            // 🧾 Simulated appointment data
            val appointmentDetails = """
                Appointment Confirmed ✅
                
                Name: $name
                Issue: $problem
                
                Date: $currentTime
                
                Doctor: Dr. Sharma (General Physician)
                
                You will be contacted soon.
            """.trimIndent()

            binding.tvResult.text = appointmentDetails

            // 🔥 OPTIONAL: Send Email Intent (realistic feel)
            sendEmail(name, problem, currentTime)
        }
    }

    private fun sendEmail(name: String, problem: String, time: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("doctor@vitamate.com"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "New Consultation Request")
        intent.putExtra(Intent.EXTRA_TEXT,
            "Patient Name: $name\nProblem: $problem\nTime: $time")

        try {
            startActivity(Intent.createChooser(intent, "Send Email"))
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "No email app found", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}