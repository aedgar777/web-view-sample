package com.weare8.webviewsample

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class WebActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)


        val webView = findViewById<WebView>(R.id.webView)

        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.loadUrl("https://ee.s.weare8.com/me/onboarding")

//        webView.loadUrl("https://weare8.com")


    }


    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, WebActivity::class.java)
            return intent
        }
    }
}