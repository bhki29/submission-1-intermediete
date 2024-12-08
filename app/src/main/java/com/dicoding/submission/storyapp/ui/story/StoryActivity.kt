package com.dicoding.submission.storyapp.ui.story

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.submission.storyapp.R
import com.dicoding.submission.storyapp.data.adapter.StoryAdapter
import com.dicoding.submission.storyapp.di.Injection

class StoryActivity : AppCompatActivity() {

    private lateinit var storyViewModel: StoryViewModel
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        storyAdapter = StoryAdapter(emptyList())
        recyclerView.adapter = storyAdapter

        // Inisialisasi ViewModel
        val repository = Injection.provideRepository(applicationContext)
        val factory = StoryViewModelFactory(repository)
        storyViewModel = ViewModelProvider(this, factory).get(StoryViewModel::class.java)

        // Observasi LiveData
        storyViewModel.isLoading.observe(this) { isLoading ->
            findViewById<ProgressBar>(R.id.progressBar).visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        storyViewModel.stories.observe(this) { stories ->
            storyAdapter = StoryAdapter(stories)
            recyclerView.adapter = storyAdapter
        }

        storyViewModel.message.observe(this) { message ->
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }

        // Memulai permintaan untuk mendapatkan cerita
        storyViewModel.getStories()
    }
}
