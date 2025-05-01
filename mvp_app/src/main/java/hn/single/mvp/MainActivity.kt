package hn.single.mvp

import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import hn.single.mvp.base.BaseActivity
import hn.single.mvp.databinding.ActivityMainBinding
import hn.single.mvp.main.MainPresenter
import hn.single.mvp.main.MainPresenterImpl
import hn.single.mvp.main.MainView

class MainActivity : BaseActivity<ActivityMainBinding>(), MainView {

    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }*/
    private lateinit var presenter: MainPresenter

    override fun initViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun setupViews() {
        presenter = MainPresenterImpl()
        presenter.attach(this)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun observeData() {

    }

    override fun setupActions() {
        binding.buttonLoad.setOnClickListener {
            presenter.loadData()
        }
    }

    override fun onDestroy() {
        presenter.detach()
        super.onDestroy()
    }

    // ---- MainView implementations ----
    override fun showLoading() {
        binding.progressBar.isVisible = true
    }

    override fun hideLoading() {
        binding.progressBar.isGone = true
    }

    override fun showData(data: String) {
        binding.textView.text = data
    }

    override fun showError(message: String) {
        showToast(message)
    }
}