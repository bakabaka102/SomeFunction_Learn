package hn.single.mvp.base

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected var toast: Toast? = null

    protected lateinit var binding: VB

    abstract fun initViewBinding(): VB

    abstract fun setupViews()

    open fun observeData() = Unit

    abstract fun setupActions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = initViewBinding()
        enableEdgeToEdge()
        setContentView(binding.root)
        setupViews()
        setupActions()
        observeData()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Call setupViews here when everything is already
        //setupViews()
    }

    fun showToast(message: String) {
        toast?.cancel()
        toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast?.show()
    }
}