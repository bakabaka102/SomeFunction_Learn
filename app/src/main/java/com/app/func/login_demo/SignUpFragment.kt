package com.app.func.login_demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import com.app.func.R

class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_sign_up, container, false)
        root.findViewById<Button>(R.id.btnCreateAccount).setOnClickListener {
            val navHostFragment: NavHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val mNavController = navHostFragment.navController
            mNavController.navigate(R.id.homeFragment)
        }
        return root
    }
}