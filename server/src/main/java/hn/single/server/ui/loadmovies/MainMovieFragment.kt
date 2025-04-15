package hn.single.server.ui.loadmovies

import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hn.single.server.base.BaseFragment
import hn.single.server.common.UIState
import hn.single.server.databinding.FragmentMainMovieBinding
import hn.single.server.ui.loadmovies.viewmodel.MainViewModel
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainMovieFragment : BaseFragment<FragmentMainMovieBinding>() {

    private var adapter: MainAdapter = MainAdapter()

    private val mainViewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun getViewBinding() = FragmentMainMovieBinding.inflate(layoutInflater)

    override fun setUpViews() {
        collector()
        binding?.apply {
            rv.adapter = adapter
            rv.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            rv.addItemDecoration(
                DividerItemDecoration(
                    requireActivity(),
                    DividerItemDecoration.VERTICAL,
                )
            )
        }

    }

    override fun observeData() {

    }

    override fun observeView() {

    }

    override fun initActions() {

    }

    private fun collector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.mainItem.collect {
                    when (it) {
                        is UIState.Success -> {
                            binding?.progress?.isVisible = false
                            binding?.error?.isVisible = false
                            binding?.rv?.isVisible = true
                            adapter.setItems(it.data)
                        }

                        is UIState.Failure -> {
                            binding?.progress?.isVisible = false
                            binding?.error?.isVisible = true
                            binding?.rv?.isVisible = false
                            binding?.error?.text = it.throwable.toString()
                        }

                        is UIState.Loading -> {
                            binding?.progress?.isVisible = true
                            binding?.error?.isVisible = false
                            binding?.rv?.isVisible = false
                        }
                    }
                }
            }
        }
    }
}