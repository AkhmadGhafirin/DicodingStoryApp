package com.cascer.dicodingstoryapp.ui.story.list

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cascer.dicodingstoryapp.data.model.StoryDataModel
import com.cascer.dicodingstoryapp.databinding.ItemStoryBinding
import com.cascer.dicodingstoryapp.utils.ImageUtils.load

class StoryAdapter(
    private val listener: (story: StoryDataModel, optionsCompat: ActivityOptionsCompat) -> Unit
) : RecyclerView.Adapter<StoryAdapter.StoryViewHolder>() {

    private var listItem = mutableListOf<StoryDataModel>()

    fun sendData(data: List<StoryDataModel>) {
        val diffCallback = StoryDiffCallback(listItem, data)
        val diffStory = DiffUtil.calculateDiff(diffCallback)
        listItem.clear()
        listItem.addAll(data)
        diffStory.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        listItem[position].let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryViewHolder {
        return StoryViewHolder(
            ItemStoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = listItem.size

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
                listener.invoke(listItem[adapterPosition], optionsCompat)
            }
        }

        fun bind(item: StoryDataModel) {
            with(binding) {
                ivPhoto.load(binding.root.context, item.photoUrl)
                tvName.text = item.name
            }
        }
    }
}