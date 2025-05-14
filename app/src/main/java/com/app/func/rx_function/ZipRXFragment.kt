package com.app.func.rx_function

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentSimpleRxBinding
import com.app.func.rx_function.model.User
import com.app.func.rx_function.utils.AppConstant
import com.app.func.rx_function.utils.Utils
import com.app.func.utils.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.BiFunction
import io.reactivex.rxjava3.schedulers.Schedulers

class ZipRXFragment : BaseFragment<FragmentSimpleRxBinding>(), View.OnClickListener {

    override fun getViewBinding() = FragmentSimpleRxBinding.inflate(layoutInflater)

    override fun setUpViews() {

    }

    override fun initActions() {
        binding.doSomeWork.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        if (view == binding.doSomeWork) {
            binding.loadingView.isVisible = true
            Handler(Looper.getMainLooper()).postDelayed({
                doSomeWork()
            }, 1000L)

        }
    }

    /*
     * Here we are getting two user list
     * One, the list of cricket fans
     * Another one, the list of football fans
     * Then we are finding the list of users who loves both
     */
    private fun doSomeWork() {
        Observable.zip(
            getCricketFansObservable(), getFootballFansObservable(),
            BiFunction { cricketFans, footballFans ->
                return@BiFunction Utils.filterUserWhoLovesBoth(cricketFans, footballFans)
            })
            // Run on a background thread
            .subscribeOn(Schedulers.io())
            // Be notified on the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(getObserver())
    }

    private fun getObserver(): Observer<List<User>> {
        return object : Observer<List<User>> {

            override fun onSubscribe(d: Disposable) {
                Logger.d("onSubscribe : " + d.isDisposed)
            }

            override fun onNext(userList: List<User>) {
                binding.textView.append(" onNext")
                binding.textView.append(AppConstant.LINE_SEPARATOR)
                for (user in userList) {
                    binding.textView.append(" firstname : ${user.firstname}")
                    binding.textView.append(AppConstant.LINE_SEPARATOR)
                }
                Logger.d("onNext : " + userList.size)
            }

            override fun onError(e: Throwable) {
                binding.textView.append(" onError : " + e.message)
                binding.textView.append(AppConstant.LINE_SEPARATOR)
                Logger.d("onError : " + e.message)
                binding.loadingView.isVisible = false
            }

            override fun onComplete() {
                binding.textView.append(" onComplete")
                binding.textView.append(AppConstant.LINE_SEPARATOR)
                Logger.d("onComplete")
                binding.loadingView.isVisible = false
            }
        }
    }

    private fun getFootballFansObservable(): Observable<List<User>> {
        return Observable.create {
            if (!it.isDisposed) {
                it.onNext(Utils.getUserListWhoLovesFootball())
                it.onComplete()
            }
        }.subscribeOn(Schedulers.io())
    }

    private fun getCricketFansObservable(): Observable<List<User>> {
        return Observable.create {
            if (!it.isDisposed) {
                it.onNext(Utils.getUserListWhoLovesCricket())
                it.onComplete()
            }
        }.subscribeOn(Schedulers.io())
    }
}