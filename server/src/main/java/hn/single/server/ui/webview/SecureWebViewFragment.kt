package hn.single.server.ui.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import hn.single.server.base.BaseFragment
import hn.single.server.databinding.FragmentSecureWebViewBinding

@AndroidEntryPoint
class SecureWebViewFragment : BaseFragment<FragmentSecureWebViewBinding>() {

    private lateinit var urlToLoad: String
    private val args: SecureWebViewFragmentArgs by navArgs()

    override fun shouldWrapWithToolbar(): Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //urlToLoad = arguments.getString("url") ?: "https://example.com"
        urlToLoad = args.url
        showToast("url is: $urlToLoad")
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupWebView()
    }

    private fun setupToolbarDetail() {
        binding.toolbar.setNavigationOnClickListener {
            if (binding.webView.canGoBack() == true) {
                binding.webView.goBack()
            } else {
                findNavController().popBackStack()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        with(binding.webView) {
            this.settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                javaScriptCanOpenWindowsAutomatically = false
                allowFileAccess = false
                allowContentAccess = false
                setSupportZoom(false)
                builtInZoomControls = false
                displayZoomControls = false
                mediaPlaybackRequiresUserGesture = true
                mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
            }

            this.webViewClient = object : WebViewClient() {
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    binding.progressBar.visibility = View.VISIBLE
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    binding.progressBar.visibility = View.GONE
                }

                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return false
                }
            }

            this.loadUrl(urlToLoad)
        }
    }

    override fun getViewBinding() = FragmentSecureWebViewBinding.inflate(layoutInflater)

    override fun setUpViews() {
        setupToolbarDetail()
        setupWebView()
    }

    override fun isBottomNavVisible(): Boolean = false

}
