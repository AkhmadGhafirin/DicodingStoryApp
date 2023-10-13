package com.cascer.dicodingstoryapp.ui.story

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cascer.dicodingstoryapp.databinding.ActivityStoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}