package com.dicoding.submission.storyapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.submission.storyapp.data.pref.SharedPreferencesHelper
import com.dicoding.submission.storyapp.ui.addstory.AddStoryActivity
import com.dicoding.submission.storyapp.ui.login.LoginActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Memeriksa apakah pengguna sudah login
        if (SharedPreferencesHelper.isLoggedIn(this)) {
            // Jika sudah login, lanjutkan untuk menampilkan halaman utama
            setContentView(R.layout.activity_main)

            // Menambahkan FloatingActionButton untuk menambah cerita
            val fabAddStory: FloatingActionButton = findViewById(R.id.fab_add)
            fabAddStory.setOnClickListener {
                // Arahkan pengguna ke AddStoryActivity
                val intent = Intent(this, AddStoryActivity::class.java)
                startActivity(intent)
            }
        } else {
            // Jika belum login, langsung arahkan ke LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Menutup MainActivity agar tidak bisa kembali ke halaman ini
        }
    }
}



