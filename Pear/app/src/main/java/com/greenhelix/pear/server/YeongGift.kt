package com.greenhelix.pear.server;

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.ViewGroup
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.greenhelix.pear.R

lateinit var webView: WebView
lateinit var returnValue: TextView

lateinit var handler: Handler
lateinit var dialog: Dialog

class YeongGift : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.address_api_web)

        handler = Handler(Looper.myLooper()!!)

        webView = findViewById(R.id.wv_adr_api)
        returnValue = findViewById(R.id.tv_address)

        webView.apply {
            settings.javaScriptEnabled = true
            settings.setSupportMultipleWindows(true)
            settings.javaScriptCanOpenWindowsAutomatically = true
            webChromeClient = SubChromeClient(this@YeongGift)

            addJavascriptInterface(AndroidBridge(), "TestApp")
            webView.loadUrl("http://192.168.0.5:3000/daum.html")
        }
    }

    class AndroidBridge {
        @JavascriptInterface
        fun setAddress(arg1: String, arg2: String, arg3: String) {
            handler.post {
                if(dialog.isShowing) dialog.hide()
                returnValue.text = String.format("(%s) %s %s", arg1, arg2, arg3)
            }
        }
    }

    class SubChromeClient(val context: Context): WebChromeClient() {
        override fun onCreateWindow(
                view: WebView?,
                isDialog: Boolean,
                isUserGesture: Boolean,
                resultMsg: Message?
        ): Boolean {
            val newWebView = WebView(context)
            newWebView.settings.javaScriptEnabled = true;

            newWebView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                        view: WebView,
                        url: String
                ): Boolean {
                    return false
                }
            }

            dialog  = Dialog(context).apply {
                setContentView(newWebView)
                window!!.attributes.apply {
                    width = ViewGroup.LayoutParams.MATCH_PARENT
                    height = ViewGroup.LayoutParams.MATCH_PARENT
                }
            }

            dialog.show()

            val transport = resultMsg!!.obj as WebView.WebViewTransport
            transport.webView = newWebView

            resultMsg!!.sendToTarget()
            return true
        }
    }
}