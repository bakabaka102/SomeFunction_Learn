package com.app.func.base_content

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.app.func.coroutine_demo.retrofit.aaa.DataRepository
import com.app.func.networks.IQuotableService
import com.app.func.networks.RetrofitObjectGson
import com.app.func.utils.Logger
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    val compositeDisposable = CompositeDisposable()

    protected var binding: VB? = null
    abstract fun getViewBinding(): VB

    abstract fun setUpViews()

    abstract fun observeData()

    abstract fun observeView()

    abstract fun initActions()

    open fun setTitleActionBar() {
        (activity as AppCompatActivity).supportActionBar?.title = this::class.java.simpleName
    }

    fun getRepositoryRetrofit(url: String): DataRepository {
        val apiService = RetrofitObjectGson.getRetrofit(url).create(IQuotableService::class.java)
        return DataRepository(apiService)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Logger.d("${this::class.java.simpleName} onCreateView is called...")
        binding = getViewBinding()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.d("${this::class.java.simpleName} onViewCreated is called...")
        setUpViews()
        observeData()
        observeView()
        initActions()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.d("${this::class.java.simpleName} onAttach is called...")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("${this::class.java.simpleName} onCreate is called...")
    }

    override fun onStart() {
        super.onStart()
        Logger.d("${this::class.java.simpleName} onStart is called...")
    }

    override fun onResume() {
        super.onResume()
        Logger.d("${this::class.java.simpleName} onResume is called...")
        setTitleActionBar()
    }

    override fun onPause() {
        super.onPause()
        Logger.d("${this::class.java.simpleName} onPause is called...")
    }

    override fun onStop() {
        super.onStop()
        Logger.d("${this::class.java.simpleName} onStop is called...")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        compositeDisposable.clear()
        Logger.d("${this::class.java.simpleName} onDestroyView is called...")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("${this::class.java.simpleName} onDestroy is called...")
    }

    override fun onDetach() {
        super.onDetach()
        Logger.d("${this::class.java.simpleName} onDetach is called...")
    }
}