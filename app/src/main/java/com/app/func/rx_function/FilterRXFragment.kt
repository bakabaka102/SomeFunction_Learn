package com.app.func.rx_function

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.core.view.isVisible
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentSimpleRxBinding
import com.app.func.rx_function.utils.AppConstant
import com.app.func.utils.Logger
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class FilterRXFragment : BaseFragment<FragmentSimpleRxBinding>(), View.OnClickListener {

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
     * simple example by using filter operator to emit only even value
     *
     */
    private fun doSomeWork() {
        Observable.just(1, 2, 3, 4, 5, 6)
            .filter { value ->
                return@filter value % 2 == 0
            }
            .subscribe(getObserver())
    }


    private fun getObserver(): Observer<Int> {

        return object : Observer<Int> {

            override fun onSubscribe(d: Disposable) {
                Logger.d("onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: Int) {
                binding.textView.append(" onNext : ")
                binding.textView.append(AppConstant.LINE_SEPARATOR)
                binding.textView.append(" value : $value")
                binding.textView.append(AppConstant.LINE_SEPARATOR)
                Logger.d("onNext - value : $value")
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
}