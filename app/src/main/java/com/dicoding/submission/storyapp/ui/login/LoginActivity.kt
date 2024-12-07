package com.dicoding.submission.storyapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission.storyapp.MainActivity
import com.dicoding.submission.storyapp.R
import com.dicoding.submission.storyapp.costumview.CustomEmail
import com.dicoding.submission.storyapp.costumview.CustomPassword
import com.dicoding.submission.storyapp.data.pref.SharedPreferencesHelper
import com.dicoding.submission.storyapp.data.repository.AuthRepository
import com.dicoding.submission.storyapp.data.retrofit.ApiConfig
import com.dicoding.submission.storyapp.ui.register.RegisterActivity
import com.google.android.material.button.MaterialButton


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cek apakah pengguna sudah login
        if (SharedPreferencesHelper.isLoggedIn(this)) {
            // Jika sudah login, langsung arahkan ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Tutup LoginActivity agar tidak bisa kembali ke halaman ini
            return
        }

        setContentView(R.layout.activity_login)

        val emailEditText = findViewById<CustomEmail>(R.id.emailEditText)
        val passwordEditText = findViewById<CustomPassword>(R.id.customPassword)
        val loginButton = findViewById<MaterialButton>(R.id.btn_login)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val txtGoToRegister = findViewById<TextView>(R.id.txtGoToRegister)

        // Initialize ViewModel
        val apiService = ApiConfig.getApiService()
        val repository = AuthRepository(apiService)
        val factory = LoginViewModelFactory(repository)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        // Observe loading state
        loginViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observe messages (success or error)
        loginViewModel.message.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Handle login button click
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.login(email, password)
            } else {
                Toast.makeText(this, "Please enter valid email and password", Toast.LENGTH_SHORT).show()
            }
        }

        // Observe login response
        loginViewModel.loginResponse.observe(this) { response ->
            if (response != null && !response.error!!) {
                // Save token and login status
                response.loginResult?.token?.let {
                    SharedPreferencesHelper.saveLoginSession(this, it)
                }

                // Redirect to MainActivity after successful login
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish() // Close LoginActivity to prevent returning to it
            }
        }

        // TextView untuk pindah ke RegisterActivity
        txtGoToRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}

