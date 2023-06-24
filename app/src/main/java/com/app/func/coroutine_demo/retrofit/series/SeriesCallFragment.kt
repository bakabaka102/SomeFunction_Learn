package com.app.func.coroutine_demo.retrofit.series

import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.func.base_content.BaseFragment
import com.app.func.coroutine_demo.data.model.QuoteListResponse
import com.app.func.coroutine_demo.retrofit.base.ApiConstants
import com.app.func.coroutine_demo.retrofit.base.RetrofitObject
import com.app.func.coroutine_demo.retrofit.base.RetrofitService
import com.app.func.coroutine_demo.retrofit.single.QuotesAdapter
import com.app.func.databinding.FragmentSingleCallNetworkBinding
import com.app.func.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeriesCallFragment : BaseFragment<FragmentSingleCallNetworkBinding>() {

    private var quoteAdapter: QuotesAdapter = QuotesAdapter()
    override fun getViewBinding(): FragmentSingleCallNetworkBinding {
        return FragmentSingleCallNetworkBinding.inflate(layoutInflater)
    }

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
        RetrofitObject.getRetrofit(ApiConstants.BASE_URL_QUOTE).create(RetrofitService::class.java)
            .getQuoteNormal().enqueue(object : Callback<QuoteListResponse> {
                override fun onResponse(
                    call: Call<QuoteListResponse>,
                    response: Response<QuoteListResponse>
                ) {
                    val valueGet: QuoteListResponse? = response.body()
                    Logger.d("Okie, thanh cong   --- ${valueGet?.results}")
                    valueGet?.results?.let { quoteAdapter.setData(it) }
                }

                override fun onFailure(call: Call<QuoteListResponse>, t: Throwable) {
                    Logger.d("Co loi xay ra   --- ")
                }
            })
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
    }
}