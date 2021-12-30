package com.mrkenhoo.covidcertificate

import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        val webView = findViewById<WebView>(R.id.webView)

        webView.settings.javaScriptEnabled = true
        webView.settings.safeBrowsingEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.allowFileAccess = true
        webView.loadUrl("https://www3.gobiernodecanarias.org/aplicaciones/scs/certificadocovid/")

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                if (view != null) {
                    view.visibility = View.INVISIBLE
                    progressBar.visibility = View.VISIBLE
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (view != null ) {
                    view.visibility = View.VISIBLE
                    progressBar.visibility = View.INVISIBLE
                }
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

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        val webView = findViewById<WebView>(R.id.webView)
        if (webView.canGoBack()) {
            webView.canGoBack()
        }
        return super.onKeyDown(keyCode, event)
    }
}
