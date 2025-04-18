package com.app.func.features.viewpagers

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.app.func.features.viewpagers.model.Tutorial

class TutorialPagerAdapter(
    private val tutorialList: List<Tutorial>, fm: FragmentManager
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int = tutorialList.size

    override fun getItem(position: Int): Fragment {
        return TutorialFragment.newInstance(tutorialList[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tutorialList[position].name
    }
}