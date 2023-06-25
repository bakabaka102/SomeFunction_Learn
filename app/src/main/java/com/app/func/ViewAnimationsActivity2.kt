package com.app.func

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.app.func.base_content.BaseActivity
import com.app.func.databinding.ActivityViewAnimations2Binding

class ViewAnimationsActivity2 : BaseActivity<ActivityViewAnimations2Binding>() {

    override fun instanceViewBinding(): ActivityViewAnimations2Binding {
        return ActivityViewAnimations2Binding.inflate(layoutInflater)
    }

    override fun getNavController(): NavController {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerAnimationView) as NavHostFragment
        return navHostFragment.navController
    }


}