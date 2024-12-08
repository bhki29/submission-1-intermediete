package com.dicoding.submission.storyapp.ui.detail

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.dicoding.submission.storyapp.R
import com.dicoding.submission.storyapp.data.response.Story
import com.dicoding.submission.storyapp.data.retrofit.ApiConfig
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val storyId = intent.getStringExtra(EXTRA_STORY_ID)
        if (storyId != null) {
            fetchStoryDetail(storyId)
        } else {
            Toast.makeText(this, "Story ID not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun fetchStoryDetail(storyId: String) {
        val apiService = ApiConfig.getApiService()
        lifecycleScope.launch {
            try {
                val response = apiService.getStoryDetail(storyId)
                if (response.error == true) {
                    Toast.makeText(this@DetailActivity, response.message, Toast.LENGTH_SHORT).show()
                } else {
                    val story = response.story
                    if (story != null) {
                        updateUI(story)
                    } else {
                        Toast.makeText(this@DetailActivity, "Story detail is empty", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@DetailActivity, "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(story: Story) {
        findViewById<TextView>(R.id.tv_title).text = story.name
        findViewById<TextView>(R.id.tv_description).text = story.description
        Glide.with(this)
            .load(story.photoUrl)
            .into(findViewById(R.id.img_story))
    }

    companion object {
        const val EXTRA_STORY_ID = "extra_story_id"
    }
}}