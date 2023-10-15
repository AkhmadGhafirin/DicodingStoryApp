package com.cascer.dicodingstoryapp.ui.story

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cascer.dicodingstoryapp.data.model.StoryDataModel
import com.cascer.dicodingstoryapp.databinding.ItemStoryBinding

class StoryAdapter(
    private val listener: (story: StoryDataModel) -> Unit
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
                listener.invoke(listItem[adapterPosition])
            }
        }

        fun bind(item: StoryDataModel) {
            with(binding) {

            }
        }
    }
}