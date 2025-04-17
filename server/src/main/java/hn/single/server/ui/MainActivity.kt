package hn.single.server.ui

import androidx.navigation.fragment.NavHostFragment
import dagger.hilt.android.AndroidEntryPoint
import hn.single.server.R
import hn.single.server.base.BaseActivity
import hn.single.server.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var navHostFragments: Map<Int, NavHostFragment>

    override fun initViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun setupViews() {
        //findNavController(R.id.mainSerContainerView)
        navHostFragments = mapOf(
            R.id.nav_for_you to createNavHost(R.id.nav_for_you, R.navigation.nav_for_you),
            R.id.nav_finance to createNavHost(R.id.nav_finance, R.navigation.nav_finance),
            R.id.nav_following to createNavHost(R.id.nav_following, R.navigation.nav_following),
            R.id.nav_kiosk to createNavHost(R.id.nav_kiosk, R.navigation.nav_kiosk),
        )
        showFragment(R.id.nav_for_you)
        binding.mainBottomNavigation.setOnItemSelectedListener { item ->
            showFragment(item.itemId)
            true
        }
    }

    private fun createNavHost(tag: Int, navGraphId: Int): NavHostFragment {
        val tagName = tag.toString()
        val fragment = supportFragmentManager.findFragmentByTag(tagName) as? NavHostFragment
            ?: NavHostFragment.create(navGraphId).also {
                supportFragmentManager.beginTransaction()
                    .add(R.id.mainSerContainerView, it, tagName).hide(it).commitNow()
            }
        return fragment
    }

    private fun showFragment(itemId: Int) {
        navHostFragments.forEach { (id, fragment) ->
            supportFragmentManager.beginTransaction().apply {
                if (id == itemId) show(fragment) else hide(fragment)
            }.commit()
        }
    }

    override fun observeData() {

    }

    override fun setupActions() {

    }
}