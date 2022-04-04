package com.app.func.login_demo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import com.app.func.R
import com.app.func.base_content.BaseFragment
import com.app.func.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment() {

    private var binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding?.btnViewProfile?.setOnClickListener {
//        rootViewFragment.findViewById<Button>(R.id.btnViewProfile).setOnClickListener {
//            val navHostFragment: NavHostFragment =
//                activity?.supportFragmentManager?.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//            val mNavController = navHostFragment.navController
//            mNavController.navigate(R.id.snowyMainFragment)
            getNavController()?.navigate(R.id.snowyMainFragment)
//        }
        }

        binding?.btnSwipeToDelete?.setOnClickListener {
            getNavController()?.navigate(R.id.listUserFragment)
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}