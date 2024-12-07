package com.dicoding.submission.storyapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission.storyapp.MainActivity
import com.dicoding.submission.storyapp.R
import com.dicoding.submission.storyapp.data.pref.SharedPreferencesHelper
import com.dicoding.submission.storyapp.data.repository.AuthRepository


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginButton: Button
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Inisialisasi ViewModel dan Factory
        val repository = AuthRepository()  // Tidak perlu parameter apiService
        val factory = LoginViewModelFactory(repository)
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        // Inisialisasi UI
        loginButton = findViewById(R.id.btn_login)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.customPassword)

        // Tombol login diklik
        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Panggil fungsi login dari ViewModel
            loginViewModel.login(email, password)

        }

        // Observasi perubahan pada loginResponse
        loginViewModel.loginResponse.observe(this, Observer { response ->
            if (response != null) {
                if (!response.error!!) {
                    // Login berhasil
                    response.loginResult?.token?.let { token ->
                        // Simpan token dan status login
                        SharedPreferencesHelper.saveLoginSession(this, token)

                        // Login sukses, lanjutkan ke MainActivity
                        Toast.makeText(this, "Login successful! Welcome, ${response.loginResult?.name}", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish() // Menutup LoginActivity agar tidak bisa kembali
                    }
                } else {
                    // Login gagal
                    Toast.makeText(this, response.message ?: "Login failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Tampilkan pesan error jika tidak ada response
                Toast.makeText(this, "Error: No response from server", Toast.LENGTH_SHORT).show()
            }
        })
    }
}