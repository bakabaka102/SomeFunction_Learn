package hn.single.server.ui

import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import hn.single.server.R
import hn.single.server.base.BaseFragment
import hn.single.server.databinding.FragmentMainBinding

class MainFragment : BaseFragment<FragmentMainBinding>() {

    override fun getViewBinding(): FragmentMainBinding = FragmentMainBinding.inflate(layoutInflater)

    override fun setUpViews() {
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.containerViewFragmentMain) as? NavHostFragment
        navHostFragment?.navController?.let { navController ->
            binding.mainBottomNavigation.setupWithNavController(navController)
        } ?: Log.e("MainFragment", "NavHostFragment not found!")
    }
}