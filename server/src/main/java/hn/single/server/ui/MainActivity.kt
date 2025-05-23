package hn.single.server.ui

import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import hn.single.server.R
import hn.single.server.base.BaseActivity
import hn.single.server.base.BaseFragment
import hn.single.server.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun initViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initViewModel(): Class<MainViewModel> = MainViewModel::class.java

    override fun setupViews() {
        //enableEdgeToEdge()
        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        //setSupportActionBar(binding.toolbar)
        // Setup BottomNav + NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.containerViewMainActivity) as NavHostFragment
        val navController = navHostFragment.navController
        binding.mainBottomNavigation.setupWithNavController(navController)
        // Theo dõi fragment để ẩn/hiện BottomNav
        navController.addOnDestinationChangedListener { _, _, _ ->
            val currentFragment = navHostFragment.childFragmentManager.fragments.firstOrNull()
            if (currentFragment is BaseFragment<*>) {
                setBottomNavigationVisible(currentFragment.isBottomNavVisible())
            } else {
                setBottomNavigationVisible(true)
            }
        }
    }

    fun setBottomNavigationVisible(isBottomNavVisible: Boolean) {
        binding.mainBottomNavigation.isVisible = isBottomNavVisible
    }
}