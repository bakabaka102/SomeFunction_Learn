package com.app.func.rx_function

import android.os.Handler
import android.os.Looper
import android.view.View
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentSimpleRxBinding
import com.app.func.rx_function.utils.AppConstant
import com.app.func.utils.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TimerRXFragment : BaseFragment<FragmentSimpleRxBinding>(), View.OnClickListener {
    override fun getViewBinding() = FragmentSimpleRxBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }

    override fun observeView() {

    }

    override fun observeData() {

    }

    override fun initActions() {
        binding?.doSomeWork?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view == binding?.doSomeWork) {
            binding?.loadingView?.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                doSomeWork()
            }, 1000L)

        }
    }

    /*
     * simple example using timer to do something after 2 seconds
     */
    private fun doSomeWork() {
        Observable.timer(2, TimeUnit.SECONDS)
            // Run on a background thread
            .subscribeOn(Schedulers.io())
            // Be notified on the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }

    private fun getObservable(): Observable<Long> {
        return Observable.timer(2, TimeUnit.SECONDS)
    }

    private fun getObserver(): Observer<Long> {
        return object : Observer<Long> {

            override fun onSubscribe(d: Disposable) {
                Logger.d("onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: Long) {
                binding?.textView?.append(" onNext : value : $value")
                binding?.textView?.append(AppConstant.LINE_SEPARATOR)
                Logger.d("onNext : value : $value")
            }

            override fun onError(e: Throwable) {
                binding?.textView?.append(" onError : " + e.message)
                binding?.textView?.append(AppConstant.LINE_SEPARATOR)
                Logger.d("onError : " + e.message)
                binding?.loadingView?.visibility = View.GONE
            }

            override fun onComplete() {
                binding?.textView?.append(" onComplete")
                binding?.textView?.append(AppConstant.LINE_SEPARATOR)
                Logger.d("onComplete")
                binding?.loadingView?.visibility = View.GONE
            }
        }
    }

}