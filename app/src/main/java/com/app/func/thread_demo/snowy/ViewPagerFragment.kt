package com.app.func.thread_demo.snowy

import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentViewPagerBinding
import com.app.func.thread_demo.snowy.model.Tutorial
import com.app.func.thread_demo.snowy.model.getTutorialData
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding>() {

    private var tutorialPagerAdapter: TutorialPagerAdapter? = null
    private var pagerAdapter: TutorialPager2Adapter2? = null

    override fun getViewBinding() = FragmentViewPagerBinding.inflate(layoutInflater)

    override fun setUpViews() {
        val tutorialList = getTutorialData(activity)
        tutorialPagerAdapter = activity?.supportFragmentManager?.let {
            TutorialPagerAdapter(tutorialList, it)
        }
        binding?.viewPager?.adapter = tutorialPagerAdapter
        binding?.tabLayout?.setupWithViewPager(binding?.viewPager)

        /*binding?.tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding?.viewPager?.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })*/

        pagerAdapter = TutorialPager2Adapter2(this)
        binding?.viewPager2?.adapter = pagerAdapter
        pagerAdapter?.submitList(tutorialList)
        binding?.let {
            TabLayoutMediator(it.tabLayout2, it.viewPager2) { tab, position ->
                tab.text = tutorialList[position].name
            }.attach()
        }
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

    }

    companion object {
        @JvmStatic
        fun newInstance() = ViewPagerFragment()
    }

}