package com.app.func.login_demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import com.app.func.R

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val rootViewFragment = inflater.inflate(R.layout.fragment_home, container, false)
        rootViewFragment.findViewById<Button>(R.id.btnViewProfile).setOnClickListener {
            val navHostFragment: NavHostFragment =
                activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            val mNavController = navHostFragment.navController
            mNavController.navigate(R.id.snowyMainFragment)
        }
        return rootViewFragment
    }
}