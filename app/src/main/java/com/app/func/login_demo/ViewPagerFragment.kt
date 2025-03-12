package com.app.func.login_demo

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.ViewPagerFragmentBinding
import com.app.func.login_demo.adapter.ViewPagerAdapter
import com.app.func.login_demo.tab_viewpager.TabFirstFragment
import com.app.func.utils.Logger
import com.app.func.utils.MyToast
import com.google.android.material.tabs.TabLayoutMediator

data class TabInfo(
    val title: String,
    val fragment: Fragment,
)

class ViewPagerFragment : BaseFragment<ViewPagerFragmentBinding>() {

    private var viewPagerAdapter: ViewPagerAdapter? = null
    var listTab = mutableListOf<TabInfo>()
    val maxTab = 5

    override fun setTitleActionBar() {
        super.setTitleActionBar()
    }

    override fun getViewBinding() = ViewPagerFragmentBinding.inflate(layoutInflater)

    override fun setUpViews() {
        viewPagerAdapter = ViewPagerAdapter(this)
        listTab = mutableListOf(
            TabInfo("Tab 1", TabFirstFragment.newFragment()),
            TabInfo("Tab 2", TabFirstFragment.newFragment()),
            TabInfo("Tab 3", TabFirstFragment.newFragment()),
        )
        viewPagerAdapter?.submitFragments(listTab)
        binding?.viewPager2DynamicTab?.adapter = viewPagerAdapter
        binding?.tabViewPager?.let { tabLayout ->
            binding?.viewPager2DynamicTab?.let { viewPager ->
                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = listTab[position].title
                }
            }
        }?.attach()
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {
        binding?.btnAddTab?.setOnClickListener {
            val fragment = TabFirstFragment.newFragment()
            addNewTab(fragment)
        }
        binding?.btnRemoveTab?.setOnClickListener {
            removeTab()
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
            Logger.d("No longer any tab")
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