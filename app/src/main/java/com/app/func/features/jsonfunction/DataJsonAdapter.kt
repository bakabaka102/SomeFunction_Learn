package com.app.func.features.jsonfunction

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.func.base_content.DiffCallBack

class DataJsonAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val listFragment = mutableListOf<Pair<String,Fragment>>()

    fun fillFragmentList(list: List<Pair<String, Fragment>>) {
        val diffResult = calculateDiff(list)
        listFragment.clear()
        listFragment.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun calculateDiff(newList: List<Pair<String, Fragment>>): DiffUtil.DiffResult = DiffUtil.calculateDiff(
        DiffCallBack(
            oldList = listFragment,
            newList = newList,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.first == newItem.first
            },
            areContentsTheSame = { oldItem, newItem ->
                oldItem == newItem
            }
        )
    )

    override fun getItemCount(): Int = listFragment.size

    override fun createFragment(position: Int): Fragment = listFragment[position].second
}