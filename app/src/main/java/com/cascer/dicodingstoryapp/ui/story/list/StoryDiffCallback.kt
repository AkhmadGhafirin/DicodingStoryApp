package com.cascer.dicodingstoryapp.ui.story.list

import androidx.recyclerview.widget.DiffUtil
import com.cascer.dicodingstoryapp.data.model.StoryDataModel

class StoryDiffCallback(
    private val oldList: List<StoryDataModel>,
    private val newList: List<StoryDataModel>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }
}