package com.devsisters.SolitaireDeckedOu.webview_screen

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Message
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.devsisters.SolitaireDeckedOu.R
import com.devsisters.SolitaireDeckedOu.room.Book
import com.devsisters.SolitaireDeckedOu.room.BookDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class GoldBookWebView : AppCompatActivity() {
    private var messageAb: ValueCallback<Array<Uri?>>? = null
    private lateinit var db: BookDb

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gold_book_web_view)

        db = BookDb(this, CoroutineScope(Dispatchers.IO + SupervisorJob()))

        val text = requireNotNull(intent.getStringExtra("text"))

        val webView = findViewById<WebView>(R.id.goldBook)

        webView.loadUrl(text)

        webView.webViewClient = GoldBookClient()
        webView.settings.javaScriptEnabled = true
        CookieManager.getInstance().setAcceptCookie(true)
        CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true)

        webView.settings.domStorageEnabled = true
        webView.settings.loadWithOverviewMode = false

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }

            //For Android API >= 21 (5.0 OS)
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri?>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                messageAb = filePathCallback
                selectImageIfNeed()
                return true
            }

            @SuppressLint("SetJavaScriptEnabled")
            override fun onCreateWindow(
                view: WebView?, isDialog: Boolean,
                isUserGesture: Boolean, resultMsg: Message
            ): Boolean {
                val newWebView = WebView(applicationContext)
                newWebView.settings.javaScriptEnabled = true
                newWebView.webChromeClient = this
                newWebView.settings.javaScriptCanOpenWindowsAutomatically = true
                newWebView.settings.domStorageEnabled = true
                newWebView.settings.setSupportMultipleWindows(true)
                val transport = resultMsg.obj as WebView.WebViewTransport
                transport.webView = newWebView
                resultMsg.sendToTarget()
                return true
            }
        }
    }

    private fun selectImageIfNeed() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = IMAGE_MIME_TYPE
        startActivityForResult(
            Intent.createChooser(intent, IMAGE_CHOOSER_TITLE),
            RESULT_CODE
        )
    }

    private inner class GoldBookClient : WebViewClient() {
        override fun onReceivedError(
            view: WebView?,
            errorCode: Int,
            description: String?,
            failingUrl: String?
        ) {
            super.onReceivedError(view, errorCode, description, failingUrl)
            if (errorCode == -2) {
                Toast.makeText(this@GoldBookWebView, "Error", Toast.LENGTH_LONG).show()
            }
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            lifecycleScope.launch(Dispatchers.IO) {
                when (db.bookDao().takeBook()?.bookSaved ?: false) {
                    true -> {}
                    false -> {
                        db.bookDao().putBook(
                            Book(bookText = url!!, bookSaved = true)
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val IMAGE_CHOOSER_TITLE = "Image Chooser"
        private const val IMAGE_MIME_TYPE = "image/*"

        private const val RESULT_CODE = 1
    }
}