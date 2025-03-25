package com.app.func.rx_function

import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentSearchByRxBinding
import com.app.func.rx_function.utils.getQueryTextChangeObservable
import com.app.func.utils.Constants
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchByRXFragment : BaseFragment<FragmentSearchByRxBinding>() {

    private fun initObservers() {
        val disposable = binding?.searchView?.getQueryTextChangeObservable()
            ?.debounce(300, TimeUnit.MILLISECONDS)
            ?.filter {
                if (it.isEmpty()) {
                    binding?.textViewResult?.text = ""
                    return@filter false
                } else {
                    return@filter true
                }
            }?.distinctUntilChanged()?.switchMap {
                dataFromNetwork(it)
                    .doOnError {
                        //Handle Error
                    }.onErrorReturn { Constants.EMPTY_STRING }
            }
            ?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                binding?.textViewResult?.text = it
            }
        disposable?.let {
            compositeDisposable.add(it)
        }
    }

    /**
     * Simulation of network data
     */
    private fun dataFromNetwork(query: String): Observable<String> {
        return Observable.just(true).delay(2, TimeUnit.SECONDS).map { query }
    }

    override fun getViewBinding() = FragmentSearchByRxBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }

    override fun observeView() {
        initObservers()
    }

    override fun observeData() {

    }

    override fun initActions() {

    }
}