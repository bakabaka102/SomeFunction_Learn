package com.app.func.coroutine_demo.retrofit.series

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.func.base_content.BaseFragment
import com.app.func.base_content.ResultState
import com.app.func.databinding.FragmentSingleCallNetworkBinding
import com.app.func.utils.Logger

class SeriesCallFragment : BaseFragment<FragmentSingleCallNetworkBinding>() {

    private var userAdapter = UserAdapter()
    private val viewModel: SeriesCallViewModel by viewModels()
    override fun getViewBinding() = FragmentSingleCallNetworkBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }

    override fun observeData() {
        initObserver()
        initRecyclerView()
    }
    
    private fun initObserver() {
        viewModel.fetchUsers()
        viewModel.resultState.observe(viewLifecycleOwner) { resultState ->
            Logger.d("state receive --- $resultState")
            when (resultState) {
                is ResultState.Loading -> {
                    Logger.d("Loading")
                    binding.also {
                        it.progressBar.isVisible = resultState.isLoading
                        it.recyclerView.isVisible = resultState.isLoading
                    }
                }

                is ResultState.Success -> {
                    Logger.d("Success")
                    resultState.data.let {
                        userAdapter.setData(it)
                    }
                    binding.let {
                        it.progressBar.isVisible = false
                        it.recyclerView.isVisible = true
                    }
                }

                is ResultState.Error -> {
                    Logger.d("Error - ${resultState.message}")
                    binding.let { binding ->
                        binding.textStatus.text = resultState.message
                        binding.textStatus.isVisible = true
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = false
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding.also {
            it.recyclerView.layoutManager = LinearLayoutManager(activity)
            it.recyclerView.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    LinearLayoutManager.VERTICAL
                )
            )
            //(binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            it.recyclerView.adapter = userAdapter
        }
    }
}