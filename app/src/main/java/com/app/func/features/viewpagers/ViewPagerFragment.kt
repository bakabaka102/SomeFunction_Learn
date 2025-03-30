package com.app.func.features.viewpagers

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentViewPagerBinding
import com.app.func.login_demo.tab_viewpager.TabFirstFragment
import com.app.func.features.viewpagers.model.TabInfo
import com.app.func.features.viewpagers.model.getTutorialData
import com.app.func.utils.Logger
import com.app.func.utils.MyToast
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : BaseFragment<FragmentViewPagerBinding>() {

    private var tutorialPagerAdapter: TutorialPagerAdapter? = null
    private var pagerAdapter: TutorialPager2Adapter2? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    private var listTab = mutableListOf(
        TabInfo("Tab 1", TabFirstFragment.newFragment()),
        TabInfo("Tab 2", TabFirstFragment.newFragment()),
        TabInfo("Tab 3", TabFirstFragment.newFragment()),
    )
    private val maxTab = 5

    override fun getViewBinding() = FragmentViewPagerBinding.inflate(layoutInflater)

    override fun setUpViews() {
        val tutorialList = getTutorialData(activity)
        tutorialPagerAdapter = activity?.supportFragmentManager?.let {
            TutorialPagerAdapter(tutorialList, it)
        }
        binding?.apply {
            viewPager.adapter = tutorialPagerAdapter
            tabLayout.setupWithViewPager(binding?.viewPager)
        }

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
        pagerAdapter?.submitList(tutorialList)
        binding?.apply {
            viewPager2.adapter = pagerAdapter
            TabLayoutMediator(tabLayout2, viewPager2) { tab, position ->
                tab.text = tutorialList[position].name
            }.attach()
        }
        viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter?.submitFragments(listTab)
        binding?.apply {
            viewPager2DynamicTab.adapter = viewPagerAdapter
            TabLayoutMediator(tabViewPager, viewPager2DynamicTab) { tab, position ->
                tab.text = listTab[position].title
            }.attach()
        }
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

        binding?.apply {
            btnAddTab.setOnClickListener {
                val fragment = TabFirstFragment.newFragment()
                addNewTab(fragment)
            }
            btnRemoveTab.setOnClickListener {
                removeTab()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, mOnBackPress)
    }

    private val mOnBackPress = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding?.viewPager2DynamicTab == null) {
                findNavController().navigateUp()
            } else {
                if (binding?.viewPager2DynamicTab?.currentItem == 0) {
                    findNavController().navigateUp()
                } else {
                    binding?.viewPager2DynamicTab?.currentItem =
                        binding?.viewPager2DynamicTab?.currentItem?.minus(1) ?: 0
                }
            }
        }
    }

    private fun removeTab() {
        if (listTab.isNotEmpty()) {
            listTab.apply {
                removeAt(size - 1)
            }.run {
                Logger.d("listTab size after removed: ${listTab.size}")
                viewPagerAdapter?.submitFragments(this)
            }
        } else {
            MyToast.showToast(requireContext(), "No longer any tab")
        }
    }

    private fun addNewTab(fragment: Fragment) {
        val name = binding?.edtNameTab?.text?.toString()?.trim()
        if (name.isValidData()) {
            if (canAddMoreTab()) {
                listTab.add(TabInfo(name.toString(), fragment))
                viewPagerAdapter?.submitFragments(listTab)
            } else {
                MyToast.showToast(requireContext(), "Max tab is $maxTab")
            }
        } else {
            MyToast.showToast(requireContext(), "Input invalid......")
        }
    }

    private fun String?.isValidData(): Boolean = this?.isNotBlank() == true

    private fun canAddMoreTab(): Boolean = listTab.size < maxTab
}