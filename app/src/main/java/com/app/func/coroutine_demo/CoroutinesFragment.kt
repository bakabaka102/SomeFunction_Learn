package com.app.func.coroutine_demo

import android.view.View
import androidx.navigation.fragment.findNavController
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentCoroutinesBinding

class CoroutinesFragment : BaseFragment<FragmentCoroutinesBinding>(), View.OnClickListener {
    
    override fun onClick(view: View?) {
        when (view) {
            binding.btnSingleCallNetwork -> {
                findNavController().navigate(R.id.singleCallNetworkFragment)
            }

            binding.btnSeriesCallNetwork -> {
                findNavController().navigate(R.id.seriesCallFragment)
            }

            binding.btnParallelCallNetwork -> {

            }

            binding.btnRoomDatabase -> {

            }

            binding.btnTimeOut -> {

            }

            binding.btnTryCatchError -> {

            }

            binding.btnExceptionHandler -> {

            }

            binding.btnIgnoreAndContinue -> {

            }

            binding.btnLongRunTask -> {

            }

            binding.btnTwoLongRunTask -> {

            }
        }
    }

    override fun getViewBinding() = FragmentCoroutinesBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }
    
    override fun initActions() {
        binding.btnSingleCallNetwork.setOnClickListener(this)
        binding.btnSeriesCallNetwork.setOnClickListener(this)
        binding.btnParallelCallNetwork.setOnClickListener(this)
        binding.btnRoomDatabase.setOnClickListener(this)
        binding.btnTimeOut.setOnClickListener(this)
        binding.btnTryCatchError.setOnClickListener(this)
        binding.btnExceptionHandler.setOnClickListener(this)
        binding.btnIgnoreAndContinue.setOnClickListener(this)
        binding.btnLongRunTask.setOnClickListener(this)
        binding.btnTwoLongRunTask.setOnClickListener(this)
    }
}