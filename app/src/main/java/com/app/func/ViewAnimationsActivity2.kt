package com.app.func

//import androidx.navigation.findNavController
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.fragment
import com.app.func.base_content.BaseActivity
import com.app.func.databinding.ActivityViewAnimations2Binding
import com.app.func.features.animations_fragment.MainAnimationFragment
import com.app.func.navigation.Screen
import com.app.func.view.animations_custom.BubbleAnimationFragment
import com.app.func.view.animations_custom.BubbleEmitterFragment

/**
 *
 * https://github.com/android/animation-samples/tree/main
 */
class ViewAnimationsActivity2 : BaseActivity<ActivityViewAnimations2Binding>() {

    override fun instanceViewBinding(): ActivityViewAnimations2Binding {
        return ActivityViewAnimations2Binding.inflate(layoutInflater)
    }

    override fun initViews() {
        /* val navController = supportFragmentManager
           .findFragmentById(R.id.fragmentContainerAnimationView)
           ?.findNavController()*/
        initNavigation()
    }

    private fun initNavigation() {
        //val navController = findNavController(R.id.fragmentContainerAnimationView)
        var mNavController: NavController? = null
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerAnimationView) as NavHostFragment
        mNavController = navHostFragment.navController

        val navController =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerAnimationView) as NavHostFragment).findNavController()
        navController.graph = navController.createGraph(
            startDestination = Screen.MAIN_ANIMATION.name
        ) {
            fragment<MainAnimationFragment>(Screen.MAIN_ANIMATION.name) {
                label = resources.getString(R.string.main_animation_screen)
            }
            fragment<BubbleAnimationFragment>(Screen.BUBBLE_ANIMATION.name) {
                label = resources.getString(R.string.bubble_animation_screen)
            }
            fragment<BubbleEmitterFragment>(Screen.BUBBLE_EMITTER_ANIMATION.name) {
                label = resources.getString(R.string.bubble_emitter_animation_screen)
                argument("itemId") {
                    type = NavType.StringType
                    defaultValue = "default_item"
                }
            }
        }
    }
}