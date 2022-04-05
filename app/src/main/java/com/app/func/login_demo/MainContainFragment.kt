package com.app.func.login_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.MainContainFragmentBinding

class MainContainFragment : BaseFragment() {

    private lateinit var binding: MainContainFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = MainContainFragmentBinding.inflate(inflater, container, false)

        initActions()
        return binding.root
    }

    private fun initActions() {
        binding.btnHome.setOnClickListener {
            getNavController()?.navigate(R.id.homeFragment)
        }
        binding.btnSignIn.setOnClickListener {
            getNavController()?.navigate(R.id.signInFragment)
        }
        binding.btnSignUp.setOnClickListener {
            getNavController()?.navigate(R.id.signUpFragment)
        }
        binding.btnGotoRecyclerView.setOnClickListener {
            getNavController()?.navigate(R.id.listUserFragment)
        }
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        // TODO: Use the ViewModel
    }

    companion object {
        fun newInstance() = MainContainFragment()
    }


}