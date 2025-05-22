package hn.single.server.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.appbar.MaterialToolbar
import hn.single.server.R
import hn.single.server.ui.MainActivity

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    private var _rootView: View? = null
    private val rootView get() = _rootView!!

    private var toast: Toast? = null

    abstract fun getViewBinding(): VB

    abstract fun setUpViews()

    open fun observeData() = Unit

    open fun observeView() = Unit

    open fun initActions() = Unit

    open fun shouldWrapWithToolbar(): Boolean = true

    open fun getToolbarTitle(): String? = null

    open fun isToolbarBackVisible(): Boolean = false

    abstract fun isBottomNavVisible(): Boolean

    private fun setupToolbarIfNeeded() {
        if (!shouldWrapWithToolbar()) return
        val toolbar = rootView.findViewById<MaterialToolbar>(R.id.baseToolbar) ?: return
        val title = getToolbarTitle()
        val showBack = isToolbarBackVisible()
        toolbar.isVisible = true
        toolbar.title = title ?: ""
        if (showBack) {
            toolbar.navigationIcon = AppCompatResources.getDrawable(
                requireContext(), R.drawable.ic_arrow_back
            )
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        } else {
            toolbar.navigationIcon = null
            toolbar.setNavigationOnClickListener(null)
        }
    }

    /**
     * This function is call on child fragment (so many call times).
     * Common it with setupToolbarIfNeeded() function.
     */
    fun setupToolbar(
        title: String? = null,
        showBack: Boolean = false,
        onBackClick: (() -> Unit)? = null
    ) {
        val toolbar = view?.findViewById<MaterialToolbar>(R.id.baseToolbar) ?: return
        toolbar.isVisible = true
        title?.let { toolbar.title = it }
        if (showBack) {
            toolbar.navigationIcon =
                AppCompatResources.getDrawable(requireContext(), R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener {
                onBackClick?.invoke() ?: run {
                    findNavController().popBackStack()
                }
            }
        } else {
            toolbar.navigationIcon = null
            toolbar.setNavigationOnClickListener(null)
        }
    }

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
            v.setPadding(v.paddingLeft, v.paddingTop, v.paddingRight, paddingBottom)
            insets
        }
    }

    private fun getBottomNavHeight(): Int {
        return resources.getDimensionPixelSize(R.dimen.bottom_nav_height)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        //Logger.d("${this::class.java.simpleName} onCreateView is called...")
        _binding = getViewBinding()
        return if (shouldWrapWithToolbar()) {
            // Inflate layout contain Toolbar
            val wrapper = inflater.inflate(R.layout.fragment_base, container, false)
            val containerFrame = wrapper.findViewById<FrameLayout>(R.id.fragmentContent)
            containerFrame.addView(binding.root)
            _rootView = wrapper // 🔥 lưu rootView để setupToolbar sau này
            wrapper
        } else {
            _rootView = binding.root // 🔥 lưu rootView để setupToolbar sau này
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Logger.d("${this::class.java.simpleName} onViewCreated is called...")
        //setupEdgeToEdge(binding.root)
        setupEdgeToEdge()
        setupToolbarIfNeeded()
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
        _rootView = null
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