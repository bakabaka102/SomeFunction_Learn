package com.app.func.startapp

import android.content.Intent
import com.app.func.R
import com.app.func.ViewAnimationsActivity2
import com.app.func.ViewCustomActivity
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentHomeStartBinding
import com.app.func.view.all_demo.EmotionalFaceView


class HomeStartFragment : BaseFragment<FragmentHomeStartBinding>() {

    override fun getViewBinding(): FragmentHomeStartBinding {
        return FragmentHomeStartBinding.inflate(layoutInflater)
    }

    override fun setUpViews() {
        binding?.happyButton?.setOnClickListener {
            binding?.emotionalFaceView?.happinessState = EmotionalFaceView.HAPPY
        }

        binding?.sadButton?.setOnClickListener {
            binding?.emotionalFaceView?.happinessState = EmotionalFaceView.SAD
        }

        binding?.btnThread?.setOnClickListener {
            getNavController()?.navigate(R.id.mainContainFragment)
        }
        binding?.btnViewCustom?.setOnClickListener {
            startActivity(Intent(requireActivity(), ViewCustomActivity::class.java))
        }
        binding?.btnParseJson?.setOnClickListener {

        }
        binding?.btnAnimation?.setOnClickListener {
            startActivity(Intent(requireContext(), ViewAnimationsActivity2::class.java))
        }
    }

    override fun observeData() {

    }

    override fun observeView() {

    }

    override fun initActions() {
        binding?.btnRX?.setOnClickListener {
            getNavController()?.navigate(R.id.rxFunctionFragment)
        }

        binding?.btnCoroutines?.setOnClickListener {
            getNavController()?.navigate(R.id.coroutinesFragment)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = HomeStartFragment()

    }
}