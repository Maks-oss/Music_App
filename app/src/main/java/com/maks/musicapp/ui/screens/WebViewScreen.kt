package com.maks.musicapp.ui.screens

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import com.maks.musicapp.BuildConfig
import com.maks.musicapp.utils.spotifyAuthorizationUrl

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WebViewScreen(navigateAction: (String) -> Unit) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            settings.userAgentString =
                "Mozilla/5.0 AppleWebKit/535.19 Chrome/56.0.0 Mobile Safari/535.19"
            settings.javaScriptEnabled = true
            webViewClient = WebViewOauthClient(navigateAction)
            loadUrl(spotifyAuthorizationUrl())
        }
    })
}

private class WebViewOauthClient(private val navigateAction: (String) -> Unit) : WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        request?.let {
            if (request.url.toString().startsWith(BuildConfig.redirectUri)) {
                request.url.getQueryParameter("code")?.let { code ->
                    navigateAction(code)
                } ?: run {
                    navigateAction("")
                }
            }
        }
        return super.shouldOverrideUrlLoading(view, request)
    }
}
