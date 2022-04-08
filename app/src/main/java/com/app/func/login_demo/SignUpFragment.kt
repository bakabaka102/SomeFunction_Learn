package com.app.func.login_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentSignUpBinding

class SignUpFragment : BaseFragment(), View.OnClickListener {

    private var binding: FragmentSignUpBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding?.btnCreateAccount?.setOnClickListener(this)
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onClick(view: View?) {
        when (view) {
            binding?.btnCreateAccount -> {
                getNavController()?.navigate(R.id.profileFragment)
            }
        }
    }
}