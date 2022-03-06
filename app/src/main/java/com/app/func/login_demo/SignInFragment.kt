package com.app.func.login_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentSignInBinding

class SignInFragment : BaseFragment(), View.OnClickListener {

    private var binding: FragmentSignInBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?

    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        binding?.btnSignIn?.setOnClickListener(this)
        binding?.btnSignUp?.setOnClickListener(this)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.btnSignIn -> {
                getNavController()?.navigate(R.id.homeFragment)
            }
            binding?.btnSignUp -> {
                getNavController()?.navigate(R.id.homeFragment)
            }
        }
    }
}