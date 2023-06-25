package com.app.func

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.app.func.base_content.BaseActivity
import com.app.func.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun instanceViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }


    override fun getNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment
        return navHostFragment.navController
    }
}