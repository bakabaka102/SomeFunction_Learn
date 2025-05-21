package hn.single.server.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.viewbinding.ViewBinding
import hn.single.server.R
import hn.single.server.ui.MainActivity

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    abstract fun getViewBinding(): VB

    abstract fun setUpViews()

    open fun observeData() = Unit

    open fun observeView() = Unit

    open fun initActions() = Unit

    open fun isBottomNavVisible(): Boolean = false

    private var toast: Toast? = null

    fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    fun Fragment.getMainNavController(): NavController? {
        val navHostFragment = activity?.supportFragmentManager
            ?.findFragmentById(R.id.containerViewMainActivity) as? NavHostFragment
        return navHostFragment?.navController
    }

    private fun setupEdgeToEdge() {
        /*ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }*/
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            val paddingBottom = if (isBottomNavVisible()) bottomInset + getBottomNavHeight() else 0

            v.setPadding(
                v.paddingLeft,
                v.paddingTop,
                v.paddingRight,
                paddingBottom
            )

            insets
        }
    }

    private fun getBottomNavHeight(): Int {
        return resources.getDimensionPixelSize(R.dimen.bottom_nav_height)
    }

    private fun setupEdgeToEdge(rootView: View) {
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Áp dụng padding cho phần tử cuộn chính
            view.setPadding(
                0,
                systemBars.top,
                0,
                systemBars.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        //Logger.d("${this::class.java.simpleName} onCreateView is called...")
        _binding = getViewBinding()
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Logger.d("${this::class.java.simpleName} onViewCreated is called...")
        //setupEdgeToEdge(binding.root)
        setupEdgeToEdge()
        setUpViews()
        observeData()
        observeView()
        initActions()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        //Logger.d("${this::class.java.simpleName} onAttach is called...")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Logger.d("${this::class.java.simpleName} onCreate is called...")
    }

    override fun onStart() {
        super.onStart()
        //Logger.d("${this::class.java.simpleName} onStart is called...")
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setBottomNavigationVisible(isBottomNavVisible())
        //Logger.d("${this::class.java.simpleName} onResume is called...")
    }

    override fun onPause() {
        super.onPause()
        //Logger.d("${this::class.java.simpleName} onPause is called...")
    }

    override fun onStop() {
        super.onStop()
        //Logger.d("${this::class.java.simpleName} onStop is called...")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        //Logger.d("${this::class.java.simpleName} onDestroyView is called...")
    }

    override fun onDestroy() {
        super.onDestroy()
        //Logger.d("${this::class.java.simpleName} onDestroy is called...")
    }

    override fun onDetach() {
        super.onDetach()
        //Logger.d("${this::class.java.simpleName} onDetach is called...")
    }
}