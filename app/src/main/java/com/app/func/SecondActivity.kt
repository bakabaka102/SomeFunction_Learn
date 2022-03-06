package com.app.func

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.app.func.base_content.BaseActivity
import com.app.func.databinding.ActivitySecondBinding

class SecondActivity : BaseActivity() {

    private lateinit var binding: ActivitySecondBinding
    private var mNavController: NavController? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavController()
    }

    private fun initNavController() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.containerViewSecond) as NavHostFragment
        mNavController = navHostFragment.navController
    }

    override fun getNavController(): NavController? = mNavController

}