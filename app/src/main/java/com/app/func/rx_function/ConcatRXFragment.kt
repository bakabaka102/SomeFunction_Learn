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

class ConcatRXFragment : BaseFragment<FragmentSimpleRxBinding>(), View.OnClickListener {

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
     * Using concat operator to combine Observable : concat maintain
     * the order of Observable.
     * It will emit all the 7 values in order
     * here - first "A1", "A2", "A3", "A4" and then "B1", "B2", "B3"
     * first all from the first Observable and then
     * all from the second Observable all in order
     */
    private fun doSomeWork() {
        val observableA = Observable.fromArray("A1", "A2", "A3", "A4")
        val observableB = Observable.fromArray("B1", "B2", "B3", "B4")

        Observable.concat(observableA, observableB)
            .subscribe(getObserver())
    }


    private fun getObserver(): Observer<String> {
        return object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Logger.d("onSubscribe : " + d.isDisposed)
            }

            override fun onNext(value: String) {
                binding.textView.append(" onNext : value : $value")
                binding.textView.append(AppConstant.LINE_SEPARATOR)
                Logger.d("onNext : value : $value")
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
                Logger.d(" onComplete")
                binding.loadingView.isVisible = false
            }
        }
    }

    companion object {
        val TAG: String = this::class.java.simpleName

        @JvmStatic
        fun newInstance() = ConcatRXFragment()
    }

}