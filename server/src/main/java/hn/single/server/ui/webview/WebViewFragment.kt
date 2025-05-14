package hn.single.server.ui.webview

import android.annotation.SuppressLint
import android.util.Log
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import hn.single.server.base.BaseFragment
import hn.single.server.databinding.FragmentWebViewBinding

class WebViewFragment : BaseFragment<FragmentWebViewBinding>() {

    private val args: WebViewFragmentArgs by navArgs()

    override fun getViewBinding()= FragmentWebViewBinding.inflate(layoutInflater)

    @SuppressLint("SetJavaScriptEnabled")
    override fun setUpViews() {
        //val url = arguments?.getString("url") ?: return
        val url = args.url.also {
            Log.d("WebViewFragment", "URL: $it")
        }

        binding?.webView?.apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }

}