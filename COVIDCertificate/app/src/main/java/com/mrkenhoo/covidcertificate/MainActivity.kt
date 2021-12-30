package com.mrkenhoo.covidcertificate

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    private fun startWebView(url: String) {
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val webView = findViewById<WebView>(R.id.webView)
        if (webView.canGoBack()) {
            webView.canGoBack()
        }
        return super.onKeyDown(keyCode, event)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val webView = findViewById<WebView>(R.id.webView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)

        webView.settings.javaScriptEnabled = true
        webView.settings.safeBrowsingEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = true
        webView.settings.cacheMode = WebSettings.LOAD_NO_CACHE
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.isScrollbarFadingEnabled = true

        webView.loadUrl("https://www3.gobiernodecanarias.org/aplicaciones/scs/certificadocovid/")

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url.toString())
                return true
            }

            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                view?.loadUrl(request?.url.toString())
                return super.shouldOverrideUrlLoading(view, request)
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
            }

            override fun onReceivedHttpError(
                view: WebView?,
                request: WebResourceRequest?,
                errorResponse: WebResourceResponse?
            ) {
                Log.v("MainActivity", "onReceivedHttpError ${errorResponse?.statusCode}")
                super.onReceivedHttpError(view, request, errorResponse)
            }

            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                Log.v("MainActivity", "onReceivedError ${error?.errorCode}")
                super.onReceivedError(view, request, error)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                view?.visibility = View.INVISIBLE
                progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            webView.clearCache(true)
            webView.clearFormData()
            webView.clearHistory()
            webView.reload()

            // Refresh only once
            swipeRefreshLayout.isRefreshing = false
        }
    }
}
