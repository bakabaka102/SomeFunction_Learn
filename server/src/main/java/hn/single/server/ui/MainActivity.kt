package hn.single.server.ui

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import hn.single.server.R
import hn.single.server.base.BaseActivity
import hn.single.server.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun initViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun setupViews() {
        /* val navController = findNavController(R.id.mainSerContainerView)
         binding.mainBottomNavigation.setupWithNavController(navController)*/
        // Làm status bar trong suốt
        //makeStatusBarTransparent()
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

    fun Activity.makeStatusBarTransparent(isLightText: Boolean = false) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = !isLightText // true = chữ đen, false = chữ trắng
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    fun NavController.navigateSafe(resId: Int, args: Bundle? = null) {
        currentDestination?.getAction(resId)?.let {
            navigate(resId, args)
        }
    }

    override fun observeData() {

    }

    override fun setupActions() {

    }
}