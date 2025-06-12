package com.app.func.base_content

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.app.func.R
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.app.func.coroutine_demo.retrofit.aaa.DataRepository
import com.app.func.networks.IQuotableService
import com.app.func.networks.RetrofitObjectGson
import com.app.func.utils.Logger
import com.google.android.material.appbar.MaterialToolbar
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    val compositeDisposable = CompositeDisposable()

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    private var _rootView: View? = null
    private val rootView get() = _rootView!!

    abstract fun getViewBinding(): VB

    abstract fun setUpViews()

    open fun observeData() = Unit

    open fun observeView() = Unit

    open fun initActions() = Unit

    open fun setTitleActionBar() {
        (activity as AppCompatActivity).supportActionBar?.title = this::class.java.simpleName
    }

    /**
     * Determines whether the back button in the toolbar should be visible.
     * Override this method in subclasses to customize the visibility of the toolbar's back button.
     *
     * @return `true` if the back button should be visible, `false` otherwise.
     */
    open fun isToolbarBackVisible(): Boolean = true

    /**
     * Retrieves the title to be displayed in the toolbar.
     * Override this method in subclasses to provide a custom toolbar title.
     *
     * @return The toolbar title as a `String`.
     */
    open fun getToolbarTitle(): String = this::class.java.simpleName

    /**
     * Determines whether the fragment should be wrapped with a toolbar.
     * Override this method in subclasses to customize the behavior.
     *
     * @return `true` if the fragment should be wrapped with a toolbar, `false` otherwise.
     */
    open fun shouldWrapWithToolbar(): Boolean = true

    private fun setUpToolBarIfNeeded() {
        if (!shouldWrapWithToolbar()) return
        val toolbar = rootView.findViewById<MaterialToolbar>(R.id.baseToolbar) ?: return
        val title = getToolbarTitle()
        val showBack = isToolbarBackVisible()
        toolbar.isVisible = true
        toolbar.title = title
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

    fun getRepositoryRetrofit(url: String): DataRepository {
        val apiService = RetrofitObjectGson.getRetrofit(url).create(IQuotableService::class.java)
        return DataRepository(apiService)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        Logger.d("${this::class.java.simpleName} onCreateView is called...")
        _binding = getViewBinding()
        return if (shouldWrapWithToolbar()) {
            // Inflate layout contain Toolbar
            val wrapper = inflater.inflate(R.layout.fragment_base, container, false)
            val containerFrame = wrapper.findViewById<FrameLayout>(R.id.fragmentBaseContent)
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
        Logger.d("${this::class.java.simpleName} onViewCreated is called...")
        val toolbar = view.findViewById<MaterialToolbar>(R.id.baseToolbar)
        // Gắn menu
        toolbar.apply {
            inflateMenu(R.menu.drawer_menu_item)
            setOnMenuItemClickListener { item ->
                 when (item.itemId) {
                    R.id.nav_account -> {
                        Logger.d("Account...")
                        true
                    }

                    R.id.nav_settings -> {
                        Logger.d("Setting...")
                        true
                    }

                    R.id.nav_logout -> {
                        Logger.d("Logout...")
                        true
                    }

                    else -> {
                        true
                        //super.onOptionsItemSelected(item)
                    }
                }
            }
        }

        // Apply padding to avoid status bar overlap
        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                v.paddingLeft,
                systemBarsInsets.top,
                v.paddingRight,
                v.paddingBottom
            )
            insets
        }

        setUpToolBarIfNeeded()
        setUpViews()
        observeData()
        observeView()
        initActions()
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        Logger.d("${this::class.java.simpleName} onAttach is called...")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d("${this::class.java.simpleName} onCreate is called...")
    }

    override fun onStart() {
        super.onStart()
        Logger.d("${this::class.java.simpleName} onStart is called...")
    }

    override fun onResume() {
        super.onResume()
        Logger.d("${this::class.java.simpleName} onResume is called...")
        setTitleActionBar()
    }

    override fun onPause() {
        super.onPause()
        Logger.d("${this::class.java.simpleName} onPause is called...")
    }

    override fun onStop() {
        super.onStop()
        Logger.d("${this::class.java.simpleName} onStop is called...")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        _rootView = null
        compositeDisposable.clear()
        Logger.d("${this::class.java.simpleName} onDestroyView is called...")
    }

    override fun onDestroy() {
        super.onDestroy()
        Logger.d("${this::class.java.simpleName} onDestroy is called...")
    }

    override fun onDetach() {
        super.onDetach()
        Logger.d("${this::class.java.simpleName} onDetach is called...")
    }
}