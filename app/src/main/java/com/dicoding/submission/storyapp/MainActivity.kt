package com.dicoding.submission.storyapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.submission.storyapp.data.pref.SharedPreferencesHelper
import com.dicoding.submission.storyapp.ui.addstory.AddStoryActivity
import com.dicoding.submission.storyapp.ui.login.LoginActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cek apakah pengguna sudah login
        if (SharedPreferencesHelper.isLoggedIn(this)) {
            // Pengguna sudah login, tampilkan UI MainActivity
            setContentView(R.layout.activity_main)

            val fabAddStory: FloatingActionButton = findViewById(R.id.fab_add)
            fabAddStory.setOnClickListener {
                val intent = Intent(this, AddStoryActivity::class.java)
                startActivity(intent)
            }

            // Tombol logout
            val logoutButton: ImageView = findViewById(R.id.btn_logout)
            logoutButton.setOnClickListener {
                // Hapus sesi login
                SharedPreferencesHelper.clearLoginSession(this)
                // Arahkan ke halaman login
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish() // Tutup MainActivity
            }
        } else {
            // Pengguna belum login, arahkan ke halaman login
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Tutup MainActivity agar tidak bisa kembali ke halaman ini
        }
    }
}




