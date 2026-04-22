package com.aashu.trialvitamate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aashu.trialvitamate.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 🔥 STEP 1: CHECK IF ALREADY LOGGED IN
        val prefs = getSharedPreferences("user_prefs", MODE_PRIVATE)

        if (prefs.getBoolean("is_logged_in", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // 🔥 STEP 2: LOGIN BUTTON LOGIC
        binding.btnLogin.setOnClickListener {

            val username = binding.etUsername.text.toString().trim()

            if (username.isEmpty()) {
                binding.etUsername.error = "Enter username"
                return@setOnClickListener
            }

            // 🔥 SAVE LOGIN STATE + USERNAME
            prefs.edit()
                .putBoolean("is_logged_in", true)
                .putString("username", username)
                .apply()

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}