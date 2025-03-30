package com.app.func.features.viewpagers

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.func.features.viewpagers.model.TabInfo

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private var listFragment = mutableListOf<TabInfo>()

    fun submitFragments(fragments: List<TabInfo>) {
        val diffResult = DiffUtil.calculateDiff(FragmentDiffCallback(listFragment, fragments))
        listFragment.clear()
        listFragment.addAll(fragments)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = listFragment.size

    override fun createFragment(position: Int): Fragment = listFragment[position].fragment

}