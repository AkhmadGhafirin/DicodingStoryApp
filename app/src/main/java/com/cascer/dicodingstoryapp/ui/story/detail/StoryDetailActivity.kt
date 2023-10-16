package com.cascer.dicodingstoryapp.ui.story.detail

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.cascer.dicodingstoryapp.data.model.StoryDataModel
import com.cascer.dicodingstoryapp.databinding.ActivityStoryDetailBinding
import com.cascer.dicodingstoryapp.ui.story.StoryViewModel
import com.cascer.dicodingstoryapp.utils.ImageUtils.load
import com.cascer.dicodingstoryapp.utils.gone
import com.cascer.dicodingstoryapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryDetailBinding
    private val viewModel: StoryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val storyId = intent.getStringExtra("storyId")
        viewModel.requestStory(storyId.orEmpty())
        setupViewModel()
    }

    private fun setupView(item: StoryDataModel) {
        with(binding) {
            ivPhoto.load(this@StoryDetailActivity, item.photoUrl)
            tvName.text = item.name
            tvDesc.text = item.description
        }
    }

    private fun setupViewModel() {
        with(viewModel) {
            story.observe(this@StoryDetailActivity) {
                setupView(it)
            }

            isLoading.observe(this@StoryDetailActivity) {
                if (it) {
                    binding.progressbar.visible()
                } else {
                    binding.progressbar.gone()
                }
            }

            errorMsg.observe(this@StoryDetailActivity) {
                Toast.makeText(this@StoryDetailActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}