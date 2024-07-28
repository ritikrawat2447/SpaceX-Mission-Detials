package com.example.spacex.AllScreens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.spacex.MissionViewModel

@Composable
fun StoreScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val webView by remember{ mutableStateOf<WebView?>(null) }
        val url by remember {
            mutableStateOf("https://www.spacex.com/vehicles/falcon-9/")
        }
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                    loadUrl(url)
                }
            }, update = {
                webView?.loadUrl(url)
            })


    }
}