package com.app.func.rx_function

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.func.R

class RxFunctionFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rx_function, container, false)
    }

    companion object {

        private const val TAG: String = "RxFunctionFragment"

        @JvmStatic
        fun newInstance() = RxFunctionFragment()

    }
}