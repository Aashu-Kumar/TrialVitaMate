package com.aashu.trialvitamate.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.aashu.trialvitamate.MainActivity
import com.aashu.trialvitamate.R
import com.aashu.trialvitamate.databinding.FragmentHomeBinding

import android.content.Intent
import android.app.AlertDialog
import com.aashu.trialvitamate.LoginActivity

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val features = listOf(
        "Search Medicine",
        "Predict Disease Risk",
        "Diet & Exercise",
        "What to Avoid",
        "Report Side Effects",
        "Consult Doctor"
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        //logout
        binding.btnLogout.setOnClickListener {

            val prefs = requireActivity().getSharedPreferences("user_prefs", 0)

            android.app.AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    prefs.edit().clear().apply()

                    val intent = Intent(requireActivity(), LoginActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        //logout done


        val prefs = requireActivity().getSharedPreferences("user_prefs", 0)
        val name = prefs.getString("username", "User")

        binding.tvWelcome.text = "Welcome, $name 👋"

        // 🔥 ADD THIS (new part)
        binding.recyclerView.adapter = FeatureAdapter(features) { feature ->
            when (feature) {
                "Search Medicine" -> (activity as MainActivity).replaceFragment(MedicineFragment(), true)
                "Predict Disease Risk" -> (activity as MainActivity).replaceFragment(PredictionFragment(), true)
                "Diet & Exercise" -> (activity as MainActivity).replaceFragment(DietFragment(), true)
                "What to Avoid" -> (activity as MainActivity).replaceFragment(AvoidFragment(), true)
                "Report Side Effects" -> (activity as MainActivity).replaceFragment(SideEffectFragment(), true)
                "Consult Doctor" -> (activity as MainActivity).replaceFragment(ConsultFragment(), true)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


/* package com.aashu.trialvitamate.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.aashu.trialvitamate.R
import com.aashu.trialvitamate.databinding.FragmentHomeBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHomeBinding.bind(view)

        // Grid layout (2 columns)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} */