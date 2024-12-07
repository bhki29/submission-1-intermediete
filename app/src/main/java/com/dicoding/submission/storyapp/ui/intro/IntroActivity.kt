package com.dicoding.submission.storyapp.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.submission.storyapp.MainActivity
import com.dicoding.submission.storyapp.data.pref.SharedPreferencesHelper
import com.dicoding.submission.storyapp.databinding.ActivityIntroBinding
import com.dicoding.submission.storyapp.ui.login.LoginActivity
import com.dicoding.submission.storyapp.ui.register.RegisterActivity

class IntroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cek apakah pengguna sudah login
        if (SharedPreferencesHelper.isLoggedIn(this)) {
            // Jika sudah login, langsung menuju ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Tutup IntroActivity
            return
        }

        binding.btnLoginIntro.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.btnRegisterIntro.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}