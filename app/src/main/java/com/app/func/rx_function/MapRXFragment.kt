package com.app.func.rx_function

import android.os.Handler
import android.os.Looper
import android.view.View
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentSimpleRxBinding
import com.app.func.rx_function.model.ApiUser
import com.app.func.rx_function.model.User
import com.app.func.rx_function.utils.AppConstant
import com.app.func.rx_function.utils.Utils
import com.app.func.utils.Constants
import com.app.func.utils.Logger
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MapRXFragment : BaseFragment<FragmentSimpleRxBinding>(), View.OnClickListener {

    override fun getViewBinding(): FragmentSimpleRxBinding {
        return FragmentSimpleRxBinding.inflate(layoutInflater)
    }

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
   * Here we are getting ApiUser Object from api server
   * then we are converting it into User Object because
   * may be our database support User Not ApiUser Object
   * Here we are using Map Operator to do that
   */
    private fun doSomeWork() {
        getObservable()
            // Run on a background thread
            .subscribeOn(Schedulers.io())
            // Be notified on the main thread
            .observeOn(AndroidSchedulers.mainThread())
            .map { apiUsers ->
                return@map Utils.convertApiUserListToUserList(apiUserList = apiUsers)
            }
            .subscribe(getObserver())
    }

    private fun getObservable(): Observable<List<ApiUser>> {
        return Observable.create {
            if (!it.isDisposed) {
                it.onNext(Utils.getApiUserList())
                it.onComplete()
            }
        }
    }


    private fun getObserver(): Observer<List<User>> {
        val observer = object : Observer<List<User>> {
            override fun onSubscribe(d: Disposable) {
                Logger.d("onSubscribe : ${d.isDisposed}")
            }

            override fun onNext(userList: List<User>) {
                binding?.textView?.append(" onNext")
                binding?.textView?.append(Constants.LINE_SEPARATOR)
                for (user in userList) {
                    binding?.textView?.append(" firstname : ${user.firstname}")
                    binding?.textView?.append(AppConstant.LINE_SEPARATOR)
                }
                Logger.d("onNext : value : ${userList.size}")
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
        return observer
    }
}