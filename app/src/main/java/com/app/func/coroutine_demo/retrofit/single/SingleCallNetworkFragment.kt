package com.app.func.coroutine_demo.retrofit.single

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.func.base_content.BaseFragment
import com.app.func.coroutine_demo.data.model.ApiUser
import com.app.func.databinding.FragmentSingleCallNetworkBinding
import com.app.func.utils.Logger

class SingleCallNetworkFragment : BaseFragment() {

    private var mBinding: FragmentSingleCallNetworkBinding? = null
    private val mViewModel: SingleCallNetworkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentSingleCallNetworkBinding.inflate(inflater, container, false)

        initViewModel()
        initRecyclerView()
        initObserver()
        return mBinding?.root
    }

    private fun initObserver() {

    }

    private fun initViewModel() {

        mViewModel.getUsers().observe(viewLifecycleOwner) {

        }
    }

    private fun renderList(users: List<ApiUser>) {
    }

    private fun initRecyclerView() {
        mBinding?.recyclerView?.layoutManager = LinearLayoutManager(activity)
//        mBinding?.recyclerView?.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        mBinding?.recyclerView?.addItemDecoration(
            DividerItemDecoration(
                activity,
                (mBinding?.recyclerView?.layoutManager as LinearLayoutManager).orientation
            )
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = SingleCallNetworkFragment()
    }
}