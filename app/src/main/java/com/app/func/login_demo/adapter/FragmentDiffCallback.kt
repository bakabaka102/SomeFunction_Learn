package com.app.func.login_demo.adapter

import androidx.recyclerview.widget.DiffUtil
import com.app.func.login_demo.TabInfo

class FragmentDiffCallback(
    private val oldList: List<TabInfo>,
    private val newList: List<TabInfo>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].fragment::class == newList[newItemPosition].fragment::class
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}