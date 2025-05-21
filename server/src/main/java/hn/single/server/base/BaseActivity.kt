package hn.single.server.base

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding, VM : ViewModel> : AppCompatActivity() {

    // Safe handling of ViewBinding
    private var _binding: VB? = null
    protected val binding get() = _binding!!

    protected lateinit var viewModel: VM
    private var toast: Toast? = null

    /** Initialize layout using ViewBinding */
    abstract fun initViewBinding(): VB

    /** Provide ViewModel class to create its instance */
    abstract fun initViewModel(): Class<VM>

    /** Set up views after binding is ready */
    abstract fun setupViews()

    /** Set up UI actions and listeners */
    open fun setupActions() = Unit

    /** Observe LiveData or other reactive streams (optional) */
    open fun observeData() = Unit

    /** Read bundle or intent extras if available (optional) */
    open fun initExtras() = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = initViewBinding()
        setContentView(_binding?.root)
        // Enable edge-to-edge mode if needed, Extend screen
        //enableEdgeToEdge()
        // Initialize ViewModel (supports both ViewModel and AndroidViewModel)
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[initViewModel()]

        initExtras()
        setupViews()
        setupActions()
        observeData()
    }

    private fun setupEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = true // Hoặc false nếu status bar nền tối
            isAppearanceLightNavigationBars = true // Hoặc false nếu nav bar nền tối
        }
    }

    // Optional override for logic that depends on view layout being completed
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Subclasses can override if needed
    }

    /** Show a toast message safely, canceling any previous one */
    protected fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }

    override fun onDestroy() {
        toast?.cancel()
        toast = null
        _binding = null
        super.onDestroy()
    }
}