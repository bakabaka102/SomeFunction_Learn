package com.app.func.coroutine_demo.retrofit.single

import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.func.base_content.BaseFragment
import com.app.func.coroutine_demo.data.model.Result
import com.app.func.databinding.FragmentSingleCallNetworkBinding
import com.app.func.utils.Logger

class SingleCallNetworkFragment : BaseFragment<FragmentSingleCallNetworkBinding>() {

    private var quoteAdapter: QuotesAdapter = QuotesAdapter()
    private var data: List<Result>? = null
    private val mViewModel: SingleCallNetworkViewModel by viewModels()
    override fun getViewBinding() = FragmentSingleCallNetworkBinding.inflate(layoutInflater)

    override fun setUpViews() {
        initObserver()
        initRecyclerView()
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

    }

    private fun initObserver() {
        mViewModel.quoteList.observe(viewLifecycleOwner) {
            Logger.d("quoteList ${it.body()}")
            data = it.body()?.results
            data?.let { it1 ->
                quoteAdapter.setData(it1)
            }
        }

        mViewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            binding?.textStatus?.text = it
            binding?.textStatus?.isVisible = true
        }

        mViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding?.progressBar?.visibility = View.VISIBLE
            } else {
                binding?.progressBar?.visibility = View.GONE
            }
        }

        mViewModel.getQuotes()
    }

    private fun initRecyclerView() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(activity)
//        binding?.recyclerView?.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        binding?.recyclerView?.addItemDecoration(
            DividerItemDecoration(
                activity,
                (binding?.recyclerView?.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding?.recyclerView?.adapter = quoteAdapter
        //mViewModel.getQuotes()

    }
}