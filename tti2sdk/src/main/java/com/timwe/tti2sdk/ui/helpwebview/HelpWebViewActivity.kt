package com.timwe.tti2sdk.ui.helpwebview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.timwe.tti2sdk.databinding.ActivityHelpWebviewBinding
import com.timwe.utils.onDebouncedListener

class HelpWebViewActivity: AppCompatActivity() {

    private lateinit var binding : ActivityHelpWebviewBinding
    private val urlText = "https://www.google.com"
//    private lateinit var webview: WebView
//    private lateinit var webviewProgress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    override fun onResume() {
        super.onResume()
        setupListeners()
    }

    override fun onBackPressed() {
        onBackPressedDispatcher.onBackPressed()
    }

    private fun setupView(){
        setWebViewClient(binding.webviewHelp)
        binding.webviewHelp.loadUrl(urlText)
    }

    private fun setupListeners(){
        binding.btnBackHelpWebView.onDebouncedListener {
            onBackPressedDispatcher.onBackPressed()
        }
        binding.btnReloadHelpWebView.onDebouncedListener {
            binding.webviewHelp.reload()
        }
    }

    private fun setWebViewClient(webView: WebView?){
        webView?.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.webviewProgressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.webviewProgressBar.visibility = View.GONE
            }
        }
    }


}