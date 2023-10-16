package com.cascer.dicodingstoryapp.ui.story.list

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.cascer.dicodingstoryapp.R
import com.cascer.dicodingstoryapp.data.model.StoryDataModel
import com.cascer.dicodingstoryapp.databinding.ActivityStoryBinding
import com.cascer.dicodingstoryapp.ui.authentication.LoginActivity
import com.cascer.dicodingstoryapp.ui.story.StoryViewModel
import com.cascer.dicodingstoryapp.ui.story.add.StoryAddActivity
import com.cascer.dicodingstoryapp.ui.story.detail.StoryDetailActivity
import com.cascer.dicodingstoryapp.utils.gone
import com.cascer.dicodingstoryapp.utils.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding
    private val viewModel: StoryViewModel by viewModels()
    private val storyAdapter by lazy {
        StoryAdapter { item, optionsCompat ->
            toDetail(
                item, optionsCompat
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
        setupViewModel()
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestStories()
    }

    private fun setupView() {
        with(binding) {
            rvList.apply {
                layoutManager =
                    LinearLayoutManager(this@StoryActivity, LinearLayoutManager.VERTICAL, false)
                adapter = storyAdapter
            }
            toolbar.setOnMenuItemClickListener {
                if (it.itemId == R.id.logout) {
                    viewModel.logout()
                    finishAffinity()
                    startActivity(Intent(this@StoryActivity, LoginActivity::class.java))
                    true
                } else {
                    false
                }
            }
            fabAdd.setOnClickListener {
                val intent = Intent(this@StoryActivity, StoryAddActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun toDetail(item: StoryDataModel, optionsCompat: ActivityOptionsCompat) {
        val intent = Intent(this@StoryActivity, StoryDetailActivity::class.java)
        intent.putExtra("storyId", item.id)
        startActivity(intent, optionsCompat.toBundle())
    }

    private fun setupViewModel() {
        with(viewModel) {
            stories.observe(this@StoryActivity) {
                storyAdapter.sendData(it)
            }

            isLoading.observe(this@StoryActivity) {
                if (it) {
                    binding.progressbar.visible()
                } else {
                    binding.progressbar.gone()
                }
            }

            errorMsg.observe(this@StoryActivity) {
                Toast.makeText(this@StoryActivity, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}