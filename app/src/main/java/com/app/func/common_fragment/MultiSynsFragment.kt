package com.app.func.common_fragment

import android.view.View
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentMultiSynsBinding

class MultiSynsFragment : BaseFragment<FragmentMultiSynsBinding>(), View.OnClickListener {
    override fun getViewBinding(): FragmentMultiSynsBinding {
        return FragmentMultiSynsBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {

    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {
        binding?.btnRX?.setOnClickListener(this)
        binding?.btnCoroutines?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.btnRX -> {
                getNavController()?.navigate(R.id.rxFunctionFragment)
            }

            binding?.btnCoroutines -> {
                getNavController()?.navigate(R.id.coroutinesFragment)

            }
        }
    }
}