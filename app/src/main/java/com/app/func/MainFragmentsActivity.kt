package com.app.func

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.app.func.databinding.ActivityMainFragmentsBinding
import java.util.*


class MainFragmentsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainFragmentsBinding
    private var mNavController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale("en")
        binding = ActivityMainFragmentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val f = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
        Log.d("ffff", f?.javaClass?.simpleName.toString())
//        this.supportActionBar?.title = ""
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        mNavController = navHostFragment.navController

//        TimeUnit.SECONDS.sleep(1)
//        Thread.sleep(1000L)
//        Handler(Looper.getMainLooper()).postDelayed({
//            val findNavController = findNavController(R.id.fragmentContainerView)
//            findNavController.navigate(R.id.signUpFragment)
//        }, 1000L)
    }

    // Pass "en","hi", etc.
    private fun setLocale(locale: String) {
        val myLocale = Locale(locale)
        // Saving selected locale to session - SharedPreferences.
        //saveLocale(locale)
        // Changing locale.
        Locale.setDefault(myLocale)
        val config = Configuration()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            config.setLocale(myLocale)
        } else {
            config.locale = myLocale
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            baseContext.createConfigurationContext(config)
        } else {
            baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        }
    }
}