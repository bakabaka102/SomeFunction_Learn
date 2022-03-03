package com.app.func.thread_demo.snowy

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.func.thread_demo.snowy.model.Tutorial

class TutorialPager2Adapter2(private val tutorialList: List<Tutorial>,
    private val itemsCount: Int,
    activity: AppCompatActivity
) : FragmentStateAdapter(activity) {


//    override fun getCount(): Int = itemsCount
//
//    override fun getItem(position: Int): Fragment {
//        Log.d("TutorialPagerAdapter", "getItem: position ---- $position  ----  tutorialList --- ${tutorialList.size}")
//        return TutorialFragment.newInstance(tutorialList[position])
//    }


    override fun getItemCount(): Int = tutorialList.size

    override fun createFragment(position: Int): Fragment {
        return SnowyMainFragment.newInstance()
    }


}