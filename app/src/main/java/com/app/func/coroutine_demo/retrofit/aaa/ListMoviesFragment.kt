package com.app.func.coroutine_demo.retrofit.aaa

import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.func.base_content.BaseFragment
import com.app.func.coroutine_demo.retrofit.base.ApiConstants
import com.app.func.databinding.FragmentSingleCallNetworkBinding

class ListMoviesFragment : BaseFragment<FragmentSingleCallNetworkBinding>() {

    private val movieAdapter = MovieAdapter()

    //    private var mViewModel : ListMovieViewModel by viewModels()
    private lateinit var mViewModel: ListMovieViewModel
    override fun getViewBinding() = FragmentSingleCallNetworkBinding.inflate(layoutInflater)

    override fun setUpViews() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.recyclerView?.layoutManager = layoutManager
        binding?.recyclerView?.adapter = movieAdapter

        initViewModel()

        mViewModel.movieList.observe(viewLifecycleOwner) {
            movieAdapter.setMovies(it.body() ?: emptyList())
        }

        mViewModel.errorMovie.observe(viewLifecycleOwner) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
        }

        mViewModel.loading.observe(viewLifecycleOwner) {
            if (it) {
                binding?.progressBar?.visibility = View.VISIBLE
            } else {
                binding?.progressBar?.visibility = View.GONE
            }
        }

        mViewModel.getAllMovies()
        initRecyclerView()
    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {

    }

    private fun initViewModel() {
        val mainRepository = getRepositoryRetrofit(ApiConstants.BASE_URL_ANDROID)
        mViewModel = ViewModelProvider(
            this,
            MyViewModelFactory(mainRepository)
        )[ListMovieViewModel::class.java]
    }

    private fun initRecyclerView() {


    }
}