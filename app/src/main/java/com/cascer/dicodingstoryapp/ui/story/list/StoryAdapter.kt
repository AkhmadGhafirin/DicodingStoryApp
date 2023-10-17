package com.cascer.dicodingstoryapp.ui.story.list

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cascer.dicodingstoryapp.data.model.StoryDataModel
import com.cascer.dicodingstoryapp.databinding.ItemStoryBinding
import com.cascer.dicodingstoryapp.utils.ImageUtils.load

class StoryAdapter(
    private val listener: (story: StoryDataModel, optionsCompat: ActivityOptionsCompat) -> Unit
) : PagingDataAdapter<StoryDataModel, StoryAdapter.StoryViewHolder>(DIFF_ITEM_CALLBACK) {

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(
            ItemStoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    inner class StoryViewHolder(private val binding: ItemStoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.ivPhoto, "profile"),
                        Pair(binding.tvName, "name"),
                    )
                getItem(adapterPosition)?.let { item -> listener.invoke(item, optionsCompat) }
            }
        }

        fun bind(item: StoryDataModel) {
            with(binding) {
                ivPhoto.load(binding.root.context, item.photoUrl)
                tvName.text = item.name
            }
        }
    }

    companion object {
        val DIFF_ITEM_CALLBACK = object : DiffUtil.ItemCallback<StoryDataModel>() {
            override fun areItemsTheSame(
                oldItem: StoryDataModel,
                newItem: StoryDataModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: StoryDataModel,
                newItem: StoryDataModel
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}