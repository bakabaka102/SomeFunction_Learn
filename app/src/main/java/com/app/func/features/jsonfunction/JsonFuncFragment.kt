package com.app.func.features.jsonfunction

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentJsonFuncBinding
import com.app.func.utils.Utils
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


class JsonFuncFragment : BaseFragment<FragmentJsonFuncBinding>() {

    private val jsonFile = "data_fragment.json"
    private val dataAdapter: DataJsonAdapter by lazy {
        DataJsonAdapter(this)
    }

    private fun createListFragmentData(data: DataJson): List<Pair<String, Fragment>> {
        val list = mutableListOf<Pair<String, Fragment>>()
        if (data.home.isNotEmpty()) {
            list.add(
                Pair(
                    getString(R.string.json_home),
                    ContainParseFragment.newInstance(DataTabJson.HOME, data.home.toString())
                )
            )
        }
        if (data.content.isNotEmpty()) {
            list.add(
                Pair(
                    getString(R.string.json_content),
                    ContainParseFragment.newInstance(DataTabJson.CONTENT, data.content.toString())
                )
            )
        }
        if (data.about.isNotEmpty()) {
            list.add(
                Pair(
                    getString(R.string.json_about),
                    ContainParseFragment.newInstance(DataTabJson.ABOUT, data.about.toString())
                )
            )
        }
        return list
    }

    private fun createFragmentDetail(type: DataTabJson, data: String): Fragment {
        /*val fg = ContainParseFragment.newInstance(data.home.toString())
           val args = Bundle()
           fg.arguments = args
           val bundleOf = bundleOf(DataTabJson.HOME.name to data.home.toString())*/
        val fragment = ContainParseFragment.newInstance(type, data)
        //val fragment = ContainParseFragment(type)
        val bundle = Bundle()
        bundle.putString(type.name, data)
        fragment.arguments = bundle
        return fragment
    }

    override fun getViewBinding() = FragmentJsonFuncBinding.inflate(layoutInflater)

    override fun setUpViews() {
        val jsonString = Utils.loadJsonFromAssets(requireActivity(), jsonFile)
        val dataJ = Json.decodeFromString<DataJson>(jsonString)
        val listFragment = createListFragmentData(dataJ)
        binding?.viewPager2Json?.adapter = dataAdapter
        dataAdapter.fillFragmentList(listFragment)
        TabLayoutMediator(binding?.tabLayoutJson!!, binding?.viewPager2Json!!) { tab, position ->
            tab.text = listFragment[position].first
            //tab.text = resources.getStringArray(R.array.title_fragment_json)[position]
        }.attach()
    }

    override fun observeData() {

    }

    override fun observeView() {

    }

    override fun initActions() {

    }

}