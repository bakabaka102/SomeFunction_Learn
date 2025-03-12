package com.app.func.thread_demo.snowy

import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentSnowyMainBinding
import com.app.func.thread_demo.snowy.model.Tutorial
import com.google.android.material.tabs.TabLayout

class SnowyMainFragment : BaseFragment<FragmentSnowyMainBinding>() {

    private var tutorialPagerAdapter: TutorialPagerAdapter? = null

    private fun getTutorialData(): List<Tutorial> {
        val tutorialList = arrayListOf<Tutorial>()
        tutorialList.add(
            Tutorial(
                getString(R.string.kotlin_title),
                getString(R.string.kotlin_url),
                getString(R.string.kotlin_desc)
            )
        )
        tutorialList.add(
            Tutorial(
                getString(R.string.android_name),
                getString(R.string.android_url),
                getString(R.string.android_desc)
            )
        )
        tutorialList.add(
            Tutorial(
                getString(R.string.rxkotlin_name),
                getString(R.string.rxkotlin_url),
                getString(R.string.rxkotlin_desc)
            )
        )
        tutorialList.add(
            Tutorial(
                getString(R.string.kitura_name),
                getString(R.string.kitura_url),
                getString(R.string.kitura_desc)
            )
        )
        return tutorialList
    }

    override fun getViewBinding() = FragmentSnowyMainBinding.inflate(layoutInflater)

    override fun setUpViews() {
        createTabsForTabLayout()
        tutorialPagerAdapter = activity?.supportFragmentManager?.let {
            TutorialPagerAdapter(getTutorialData(), it)
        }
        binding?.viewPager?.adapter = tutorialPagerAdapter

//        binding?.viewPager?.addOnPageChangeListener(
//            TabLayout.TabLayoutOnPageChangeListener(binding?.tabLayout)
//        )

        binding?.tabLayout?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding?.viewPager?.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

    }

    private fun createTabsForTabLayout() {
        binding?.tabLayout?.newTab()?.setText(getString(R.string.kotlin_title))?.let {
            binding?.tabLayout?.addTab(it)
        }
        binding?.tabLayout?.newTab()?.setText(getString(R.string.android_name))?.let {
            binding?.tabLayout?.addTab(it)
        }
        binding?.tabLayout?.newTab()?.setText(getString(R.string.rxkotlin_name))?.let {
            binding?.tabLayout?.addTab(it)
        }
        binding?.tabLayout?.newTab()?.setText(getString(R.string.kitura_name))?.let {
            binding?.tabLayout?.addTab(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SnowyMainFragment()
    }

}