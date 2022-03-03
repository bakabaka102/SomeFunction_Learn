package com.app.func.thread_demo.snowy

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.app.func.thread_demo.snowy.model.Tutorial

class TutorialPagerAdapter(
    private val tutorialList: List<Tutorial>,
    fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = tutorialList.size

    override fun getItem(position: Int): Fragment {
        Log.d("TutorialPagerAdapter", "getItem: position ---- $position  ----  tutorialList --- ${tutorialList.size}")
        return TutorialFragment.newInstance(tutorialList[position])
    }
}