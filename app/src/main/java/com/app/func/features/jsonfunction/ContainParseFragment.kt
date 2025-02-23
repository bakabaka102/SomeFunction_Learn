package com.app.func.features.jsonfunction

import android.os.Bundle
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentContainParseBinding
import com.app.func.utils.Logger


class ContainParseFragment(private val type: DataTabJson) :
    BaseFragment<FragmentContainParseBinding>() {

    private val key = when (type) {
        DataTabJson.HOME -> DataTabJson.HOME.name
        DataTabJson.CONTENT -> DataTabJson.CONTENT.name
        DataTabJson.ABOUT -> DataTabJson.ABOUT.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("key of fragment --- $key  and  data -------- ${arguments?.getString(key)}")

    }

    override fun getViewBinding(): FragmentContainParseBinding {
        return FragmentContainParseBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        binding?.textContentData?.text = "${type.name} \n ${arguments?.getString(key)}"
    }

    override fun observeData() {

    }

    override fun observeView() {

    }

    override fun initActions() {

    }

    companion object {
        @JvmStatic
        fun newInstance(type: DataTabJson, data: String? = null) = ContainParseFragment(type).apply {
            arguments = Bundle().apply {
                putString(key, data)
            }
        }
    }
}