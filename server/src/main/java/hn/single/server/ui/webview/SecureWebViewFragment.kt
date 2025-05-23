package hn.single.server.ui.webview

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import hn.single.server.base.BaseFragment
import hn.single.server.databinding.FragmentSecureWebViewBinding
import androidx.core.net.toUri

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
    private fun setupWebView() = with(binding.webView) {
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

        // Loading indicator
        this.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                binding.progressBar.isVisible = true
                binding.favicon.isGone = true // reset favicon mỗi lần load trang mới
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                if (view == null || !isAdded || viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED)) return
                binding.progressBar.isGone = true
            }

            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                return false
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                if (view == null || !isAdded || viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED)) return
                binding.progressBar.isGone = true
            }

        }

        // ChromeClient để nhận title và favicon
        this.webChromeClient = object : WebChromeClient() {
            override fun onReceivedTitle(view: WebView?, title: String?) {
                if (!isAdded || viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED)) return

                val finalTitle = if (title.isNullOrBlank()) {
                    try {
                        view?.url?.toUri()?.host ?: "Trang web"
                    } catch (e: Exception) {
                        "Trang web"
                    }
                } else {
                    title
                }
                binding.toolbar.title = finalTitle
            }

            override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
                if (!isAdded || viewLifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.DESTROYED)) return

                icon?.let {
                    binding.favicon.setImageBitmap(it)
                    binding.favicon.isVisible = true
                }
            }
        }

        loadUrl(urlToLoad)
    }

    override fun getViewBinding() = FragmentSecureWebViewBinding.inflate(layoutInflater)

    override fun setUpViews() {
        setupToolbarDetail()
        setupWebView()
    }

    override fun onDestroyView() {
        binding.webView.apply {
            stopLoading()
            //webViewClient = null
            // Gán WebViewClient rỗng để tránh callback sau khi view đã bị destroy
            webViewClient = WebViewClient()
            clearHistory()
            loadUrl("about:blank")
            removeAllViews()
            destroy()
        }
        super.onDestroyView()
    }

    override fun isBottomNavVisible(): Boolean = false

}
