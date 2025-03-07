package com.app.func.features.jsonfunction

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DataJsonAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val listFragment = mutableListOf<Pair<String,Fragment>>()

    fun fillFragmentList(list: List<Pair<String, Fragment>>) {
        listFragment.clear()
        listFragment.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = listFragment.size

    override fun createFragment(position: Int): Fragment = listFragment[position].second
}