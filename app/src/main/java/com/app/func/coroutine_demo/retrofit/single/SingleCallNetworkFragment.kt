package com.app.func.coroutine_demo.retrofit.single

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.func.base_content.BaseFragment
import com.app.func.base_content.ResultState
import com.app.func.coroutine_demo.data.model.QuoteListResponse
import com.app.func.networks.IQuotableService
import com.app.func.databinding.FragmentSingleCallNetworkBinding
import com.app.func.networks.ApiConstants
import com.app.func.networks.RetrofitObjectGson
import com.app.func.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//https://github.com/amitshekhariitbhu/Learn-Kotlin-Coroutines
class SingleCallNetworkFragment : BaseFragment<FragmentSingleCallNetworkBinding>() {

    private var quoteAdapter: QuotesAdapter = QuotesAdapter()
    private val mViewModel: SingleCallNetworkViewModel by viewModels()
    override fun getViewBinding() = FragmentSingleCallNetworkBinding.inflate(layoutInflater)

    override fun setUpViews() {
        initObserver()
        initRecyclerView()
    }

    override fun observeView() {

    }

    override fun observeData() {
        RetrofitObjectGson.getRetrofit(ApiConstants.BASE_URL_QUOTE).create(IQuotableService::class.java)
            .getQuoteNormal().enqueue(object : Callback<QuoteListResponse> {
                override fun onResponse(
                    call: Call<QuoteListResponse>,
                    response: Response<QuoteListResponse>
                ) {
                    response.body()?.results?.let {
                        Logger.d("Success, data = $it")
                        //quoteAdapter.setData(it)
                    }
                }

                override fun onFailure(call: Call<QuoteListResponse>, t: Throwable) {
                    Logger.d("An occur --- ${t.message}")
                }
            })
    }

    override fun initActions() {

    }

    private fun initObserver() {
        mViewModel.resultState.observe(viewLifecycleOwner) { resultState ->
            Logger.d("state receive --- $resultState")
            when (resultState) {
                is ResultState.Success -> {
                    resultState.data.body()?.results?.let {
                        Logger.d("Success, data = $it")
                        quoteAdapter.setData(it)
                    }
                    binding?.let {
                        it.progressBar.isVisible = false
                        it.recyclerView.isVisible = true
                    }
                }

                is ResultState.Loading -> {
                    binding?.let {
                        it.progressBar.isVisible = resultState.isLoading
                        it.recyclerView.isVisible = resultState.isLoading
                    }
                }

                is ResultState.Error -> {
                    binding?.let { binding ->
                        binding.textStatus.text = resultState.message
                        binding.textStatus.isVisible = true
                        binding.progressBar.isVisible = false
                        binding.recyclerView.isVisible = false
                    }
                }
            }
        }
        mViewModel.getQuotes()
    }

    private fun initRecyclerView() {
        binding?.let {
            it.recyclerView.layoutManager = LinearLayoutManager(activity)
            it.recyclerView.addItemDecoration(
                DividerItemDecoration(
                    activity,
                    (it.recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            it.recyclerView.adapter = quoteAdapter
        }
    }
}