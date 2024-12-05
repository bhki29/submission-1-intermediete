package com.dicoding.submission.storyapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.submission.storyapp.ui.addstory.AddStoryActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabAddStory: FloatingActionButton = findViewById(R.id.fab_add)

        fabAddStory.setOnClickListener {

            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }
}