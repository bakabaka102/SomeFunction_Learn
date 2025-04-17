package hn.single.server.ui.foryou

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hn.single.server.BuildConfig
import hn.single.server.R
import hn.single.server.base.BaseFragment
import hn.single.server.common.UIState
import hn.single.server.databinding.FragmentForYouBinding

@AndroidEntryPoint
class ForYouFragment : BaseFragment<FragmentForYouBinding>() {

    override fun getViewBinding() = FragmentForYouBinding.inflate(layoutInflater)

    private val viewModel: NewsViewModel by viewModels()
    private val newsAdapter = NewsAdapter()

    override fun setUpViews() {
        viewModel.loadTopHeadLines(country = "us", apiKey = BuildConfig.API_KEY)
        binding?.recyclerTopHeadlines?.apply {
            this.adapter = newsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun observeData() {
        viewModel.topHeadlines.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Loading -> {
                    binding?.loadingView?.isVisible = true
                    binding?.textStatus?.isGone = true
                }

                is UIState.Success -> {
                    binding?.loadingView?.isGone = true
                    val articles = state.data.articles
                    if (articles.isEmpty()) {
                        binding?.textStatus?.isVisible = true
                        binding?.textStatus?.text = getString(R.string.empty_data)
                    } else {
                        binding?.textStatus?.isGone = true
                        newsAdapter.submitList(articles)
                    }
                }

                is UIState.Failure -> {
                    binding?.loadingView?.isGone = true
                    binding?.textStatus?.isVisible = true
                    binding?.textStatus?.text = state.throwable.message
                }
            }
        }

    }

    override fun observeView() {

    }

    override fun initActions() {

    }

}