package hn.single.server.ui

import android.util.Log
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import hn.single.server.R
import hn.single.server.base.BaseActivity
import hn.single.server.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun initViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun setupViews() {
        showToast(viewModel.testValue)
        /* val navController = findNavController(R.id.mainSerContainerView)
         binding.mainBottomNavigation.setupWithNavController(navController)*/
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.toolbar)
        val fragment = supportFragmentManager.findFragmentById(R.id.mainSerContainerView)
        if (fragment is NavHostFragment) {
            val navController = fragment.navController
            binding.mainBottomNavigation.setupWithNavController(navController)
        } else {
            Log.e("MainActivity", "NavHostFragment not found!")
        }
        /*val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainSerContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        // Liên kết BottomNav với NavController
        binding.mainBottomNavigation.setupWithNavController(navController)*/
    }

    override fun setupActions() {

    }
}