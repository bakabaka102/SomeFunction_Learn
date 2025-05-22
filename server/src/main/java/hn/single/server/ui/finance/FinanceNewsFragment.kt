package hn.single.server.ui.finance

import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hn.single.server.R
import hn.single.server.base.BaseFragment
import hn.single.server.common.UIState
import hn.single.server.databinding.FragmentFinanceBinding

@AndroidEntryPoint
class FinanceNewsFragment : BaseFragment<FragmentFinanceBinding>() {

    private val financeAdapter = FinanceNewsAdapter()
    private val viewModel: FinanceNewsViewModel by viewModels()

    override fun getViewBinding() = FragmentFinanceBinding.inflate(layoutInflater)

    override fun getToolbarTitle(): String? = "Finance"

    override fun isToolbarBackVisible(): Boolean = false

    private fun setupRecyclerView() {
        binding.recyclerFinanceNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = financeAdapter
        }
        financeAdapter.onItemClick = {
            /*val action = FinanceNewsFragmentDirections.actionFinanceNewsFragmentToWebViewFragment(it.url)
            findNavController().navigate(action)*/
            val action =
                FinanceNewsFragmentDirections.actionFinanceNewsFragmentToSecureWebViewFragment(it.url)
            val bundle = bundleOf("url" to it.url)
            //findNavController().navigate(action)
            getMainNavController()?.navigate(R.id.secureWebViewFragment, bundle)
        }
    }

    override fun setUpViews() {
        setupRecyclerView()
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getFinanceArticles()
        }
        viewModel.getFinanceArticles()
        viewModel.response.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    binding.lottieLoadingView.isVisible = true
                }

                is UIState.Success -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.lottieLoadingView.isGone = true
                    val articles = state.data.articles
                    if (articles.isEmpty()) {
                        binding.textStatus.isVisible = true
                        binding.textStatus.text = getString(R.string.empty_data)
                    } else {
                        binding.textStatus.isGone = true
                        financeAdapter.submitList(articles)
                    }
                }

                is UIState.Failure -> {
                    binding.swipeRefreshLayout.isRefreshing = false
                    binding.lottieLoadingView.isGone = true
                    binding.textStatus.isVisible = true
                    binding.textStatus.text = state.throwable.message
                }
            }
        }
    }

    override fun isBottomNavVisible(): Boolean = true
}