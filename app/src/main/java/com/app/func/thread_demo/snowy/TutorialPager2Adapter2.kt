package com.app.func.thread_demo.snowy

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.func.thread_demo.snowy.model.Tutorial

class TutorialPager2Adapter2(
    val fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    private var tutorialList: List<Tutorial> = listOf()

    fun submitList(list: List<Tutorial>) {
        tutorialList = list
    }

    override fun getItemCount(): Int = tutorialList.size

    override fun createFragment(position: Int): Fragment {
        return TutorialFragment.newInstance(tutorialList[position])
    }


}