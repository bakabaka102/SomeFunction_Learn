package com.app.func.login_demo

import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.ViewPagerFragmentBinding
import com.app.func.login_demo.adapter.ViewPagerAdapter
import com.app.func.login_demo.tab_viewpager.TabFirstFragment
import com.app.func.login_demo.tab_viewpager.TabSecondFragment
import com.app.func.utils.MyToast
import com.google.android.material.tabs.TabLayoutMediator

class ViewPagerFragment : BaseFragment<ViewPagerFragmentBinding>() {

    private var viewPagerAdapter: ViewPagerAdapter? = null


    private val animalsArray = arrayOf(
        "Cat",
        "Dog",
        "Bird"
    )

    override fun getViewBinding(): ViewPagerFragmentBinding {
        return ViewPagerFragmentBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        viewPagerAdapter = ViewPagerAdapter(this)
        binding?.recyclerviewTab?.adapter = viewPagerAdapter

        binding?.tabViewPager?.let {
            binding?.recyclerviewTab?.let { it1 ->
                TabLayoutMediator(it, it1) { tab, position ->
                    tab.text = animalsArray[position]
                }
            }
        }?.attach()

//        addNewTab("Tab1", TabFirstFragment.newFragment())
//        addNewTab("Tab2", TabSecondFragment.newFragment())


        viewPagerAdapter?.setData(
            TabFirstFragment.newFragment(),
            TabSecondFragment.newFragment(),
            TabSecondFragment.newFragment()
        )

        binding?.btnAddTab?.setOnClickListener {
            val fragment = TabFirstFragment.newFragment()
            addNewTab("Tab", fragment)
        }
        binding?.btnRemoveTab?.setOnClickListener {
            removeTab()
        }

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, mOnBackPress)
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

    }

    private val mOnBackPress = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding?.recyclerviewTab == null) {
                findNavController().navigateUp()
            } else {
                if (binding?.recyclerviewTab?.currentItem == 0) {
                    findNavController().navigateUp()
                } else {
                    binding?.recyclerviewTab?.currentItem =
                        binding?.recyclerviewTab?.currentItem?.minus(1)!!
                }
            }
        }
    }

    private fun removeTab() {
        viewPagerAdapter?.removeFragment()
//       viewPagerAdapter?.removeFragment(1)
    }

    private fun addNewTab(title: String, fragment: Fragment) {
        if (binding?.edtNameTab?.text?.isBlank() == false) {
            binding?.tabViewPager?.newTab()?.setText(binding?.edtNameTab?.text)
                ?.let { it1 -> binding?.tabViewPager?.addTab(it1) }
            //fragment.setInfo("Tab 1")
            viewPagerAdapter?.addFragment(fragment)
        } else {
            MyToast.showToast(requireContext(), "Input invalid......")
        }
    }

    private fun disableToolTipTabLayout() {
        val tabStrip = binding?.tabViewPager?.getChildAt(0) as ViewGroup
        for (i in 0 until tabStrip.childCount) {
            TooltipCompat.setTooltipText(tabStrip.getChildAt(i), null)
        }
    }

}