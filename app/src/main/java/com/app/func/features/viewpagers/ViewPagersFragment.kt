package com.app.func.features.viewpagers

import android.content.Context
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentViewPagerBinding
import com.app.func.features.viewpagers.model.TabInfo
import com.app.func.features.viewpagers.model.getTutorialData
import com.app.func.utils.Logger
import com.app.func.utils.MyToast
import com.google.android.material.tabs.TabLayoutMediator
import com.app.func.features.viewpagers.model.RowData

class ViewPagersFragment : BaseFragment<FragmentViewPagerBinding>() {

    private var tutorialPagerAdapter: TutorialPagerAdapter? = null
    private var pagerAdapter: TutorialPager2Adapter2? = null
    private var viewPagerAdapter: ViewPagerAdapter? = null
    val data = listOf(
        Pair(
            "Tab 1",
            listOf(
                RowData("Title 1", content = "String content"),
                RowData("Title 2", content = true),
                RowData("Title 3", content = listOf("Option 1", "Option 2", "Option 3"))
            ),
        ),
        Pair(
            "Tab 1",
            listOf(
                RowData("Title 1", content = "String content"),
                RowData("Title 2", content = true),
                RowData(
                    "Title 3",
                    selectedPosition = 1,
                    content = listOf("Option 1", "Option 2", "Option 3")
                )
            )
        ), Pair(
            "Tab 3", listOf(
                RowData("Title 1", content = "String content"),
                RowData("Title 2", content = true),
                RowData(
                    "Title 3",
                    selectedPosition = 2,
                    content = listOf("Option 1", "Option 2", "Option 3")
                )
            )
        )
    )

    private var listTab = mutableListOf<TabInfo>()

    private val maxTab = 5

    override fun getViewBinding() = FragmentViewPagerBinding.inflate(layoutInflater)

    override fun setUpViews() {
        data.forEach {
            listTab.add(TabInfo(it.first, DetailPagerFragment.newFragment(it.second)))
        }

        val tutorialList = getTutorialData(activity)
        tutorialPagerAdapter = activity?.supportFragmentManager?.let {
            TutorialPagerAdapter(tutorialList, it)
        }
        binding.apply {
            viewPager.adapter = tutorialPagerAdapter
            tabLayout.setupWithViewPager(binding.viewPager)
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

        pagerAdapter = TutorialPager2Adapter2(this).apply {
            submitList(tutorialList)
        }
        binding.apply {
            viewPager2.adapter = pagerAdapter
            TabLayoutMediator(tabLayout2, viewPager2) { tab, position ->
                tab.text = tutorialList[position].name
            }.attach()
        }
        viewPagerAdapter = ViewPagerAdapter(this).apply {
            submitFragments(listTab)
        }
        binding.apply {
            viewPager2DynamicTab.adapter = viewPagerAdapter
            TabLayoutMediator(tabViewPager, viewPager2DynamicTab) { tab, position ->
                tab.text = listTab[position].title
            }.attach()
            hideKeyboardWhenChangePage()
        }
    }

    private fun FragmentViewPagerBinding.hideKeyboardWhenChangePage() {
        viewPager2DynamicTab.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                hideKeyboard()
            }
        })
    }

    override fun initActions() {
        binding.apply {
            btnAddTab.setOnClickListener {
                val fragment = DetailPagerFragment.newFragment(data[0].second)
                addNewTab(fragment)
            }
            btnRemoveTab.setOnClickListener {
                removeTab()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, mOnBackPress)
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private val mOnBackPress = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.viewPager2DynamicTab.currentItem == 0) {
                findNavController().navigateUp()
            } else {
                binding.viewPager2DynamicTab.currentItem =
                    binding.viewPager2DynamicTab.currentItem.minus(1)
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
        val name = binding.edtNameTab.text.toString().trim()
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