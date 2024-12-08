package com.dicoding.submission.storyapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dicoding.submission.storyapp.data.pref.DataStoreHelper
import com.dicoding.submission.storyapp.ui.story.StoryActivity
import com.dicoding.submission.storyapp.ui.intro.IntroActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenCreated {
            DataStoreHelper.isLoggedIn(applicationContext).collect { isLoggedIn ->
                if (!isLoggedIn) {
                    // Pengguna belum login, arahkan ke IntroActivity atau LoginActivity
                    val intent = Intent(this@MainActivity, IntroActivity::class.java)
                    startActivity(intent)
                    finish() // Tutup MainActivity agar tidak bisa kembali ke halaman ini
                }
            }
        }

        setContentView(R.layout.activity_main)

        val fabAddStory: FloatingActionButton = findViewById(R.id.fab_add)
        fabAddStory.setOnClickListener {
            val intent = Intent(this, StoryActivity::class.java)
            startActivity(intent)
        }

        val logoutButton: ImageView = findViewById(R.id.btn_logout)
        logoutButton.setOnClickListener {
            lifecycleScope.launch {
                DataStoreHelper.clearLoginSession(applicationContext)
            }
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish() // Tutup MainActivity agar pengguna tidak bisa kembali
        }
    }
}






