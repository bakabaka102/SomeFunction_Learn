package com.app.func.coroutine_demo.retrofit.series

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.func.base_content.BaseFragment
import com.app.func.base_content.UiState
import com.app.func.databinding.FragmentSingleCallNetworkBinding
import com.app.func.utils.Logger

class SeriesCallFragment : BaseFragment<FragmentSingleCallNetworkBinding>() {

    private var userAdapter = UserAdapter()
    private val viewModel: SeriesCallViewModel by viewModels()
    override fun getViewBinding() = FragmentSingleCallNetworkBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }

    override fun observeView() {

    }

    override fun observeData() {
        initObserver()
        initRecyclerView()
    }

    override fun initActions() {

    }

    private fun initObserver() {
        viewModel.fetchUsers()
        viewModel.uiState.observe(viewLifecycleOwner) { uiState ->
            Logger.d("state receive --- $uiState")
            when (uiState) {
                is UiState.Loading -> {
                    Logger.d("Loading")
                    binding?.let {
                        it.progressBar.isVisible = uiState.isLoading
                        it.recyclerView.isVisible = uiState.isLoading
                    }
                }

                is UiState.Success -> {
                    Logger.d("Success")
                    uiState.data.let {
                        userAdapter.setData(it)
                    }
                    binding?.let {
                        it.progressBar.isVisible = false
                        it.recyclerView.isVisible = true
                    }
                }

                is UiState.Error -> {
                    Logger.d("Error - ${uiState.message}")
                    binding?.let { binding ->
                        binding.textStatus.text = uiState.message
                        binding.textStatus.isVisible = true
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = false
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        binding?.let {
            it.recyclerView.layoutManager = LinearLayoutManager(activity)
            it.recyclerView.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    LinearLayoutManager.VERTICAL
                )
            )
            //(binding?.recyclerView?.layoutManager as LinearLayoutManager).orientation
            it.recyclerView.adapter = userAdapter
        }
    }
}