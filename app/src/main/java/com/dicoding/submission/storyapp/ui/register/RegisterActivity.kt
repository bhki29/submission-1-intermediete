package com.dicoding.submission.storyapp.ui.register

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.dicoding.submission.storyapp.R
import com.dicoding.submission.storyapp.data.repository.AuthRepository

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var registerButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inisialisasi ViewModel dan Factory
        val repository = AuthRepository()  // AuthRepository yang sudah menggunakan ApiConfig
        val factory = RegisterViewModelFactory(repository)
        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)

        // Inisialisasi UI
        registerButton = findViewById(R.id.btn_register)
        nameEditText = findViewById(R.id.nameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.customPassword)

        // Tombol register diklik
        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Panggil fungsi register dari ViewModel
            registerViewModel.register(name, email, password)
        }

        // Observasi perubahan pada registerResponse
        registerViewModel.registerResponse.observe(this, Observer { response ->
            if (response != null) {
                if (!response.error!!) {
                    // Register berhasil, lanjutkan ke LoginActivity
                    Toast.makeText(this, "Registration successful!", Toast.LENGTH_SHORT).show()
                    finish()  // Kembali ke LoginActivity setelah register berhasil
                } else {
                    // Tampilkan pesan error jika register gagal
                    Toast.makeText(this, response.message ?: "Registration failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Tampilkan pesan error jika tidak ada response
                Toast.makeText(this, "Error: No response from server", Toast.LENGTH_SHORT).show()
            }
        })
    }
}