package com.timwe.tti2sdk.ui.helpwebview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.timwe.init.EventType
import com.timwe.init.EventValue
import com.timwe.tti2sdk.databinding.ActivityHelpWebviewBinding
import com.timwe.tti2sdk.ui.destinations.DestinationViewModel
import com.timwe.utils.onDebouncedListener
import org.koin.androidx.viewmodel.ext.android.viewModel

class HelpWebViewActivity: AppCompatActivity() {

    private lateinit var binding : ActivityHelpWebviewBinding
    private val viewModel: HelpViewModel by viewModel()
    private var urlText = "https://webportals.cachefly.net/apac/indonesia/telkomsel/tti_v2/html/helpMenu.html"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpWebviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUrls()
        setupListeners()
        setupObservers()
        viewModel.sendEvent(eventType = EventType.SCREEN_ACCESS, eventValue = EventValue.HELP)
    }

    override fun onBackPressed() {
        finish()
    }

    private fun setupView(mainUrl: String){
        setWebViewClient(binding.webviewHelp)
        binding.webviewHelp.loadUrl(mainUrl)
    }

    private fun setupListeners(){
        binding.btnBackHelpWebView.onDebouncedListener {
            finish()
        }
        binding.btnReloadHelpWebView.onDebouncedListener {
           setupView(mainUrl = urlText)
        }
    }

    private fun setupObservers(){
        viewModel.helpInfo.observe(this){
            //urlText = it.learnMore
            //setupView(mainUrl = it.learnMore)
            setupView(mainUrl = urlText)
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