package com.example.richtexteditor

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.richtexteditor.databinding.ActivitySummerNoteBinding
import org.apache.commons.text.StringEscapeUtils

class SummerNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySummerNoteBinding

    var fileChooserResultLauncher = createFileChooserResultLauncher()
    var fileChooserValueCallback: ValueCallback<Array<Uri>>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummerNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.editorWebView.settings.javaScriptEnabled = true
        binding.editorWebView.settings.allowFileAccess = true

        binding.editorWebView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                try {
                    fileChooserValueCallback = filePathCallback;
                    fileChooserResultLauncher.launch(fileChooserParams?.createIntent())
                } catch (e: ActivityNotFoundException) {
                    //TODO handle intent not found error
                }
                return true
            }
        }
        binding.editorWebView.loadUrl("file:///android_asset/summernote/main.html")

        binding.btnPreview.setOnClickListener { openPreview() }
    }

    private fun createFileChooserResultLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                fileChooserValueCallback?.onReceiveValue(arrayOf(Uri.parse(it?.data?.dataString)));
            } else {
                fileChooserValueCallback?.onReceiveValue(null)
            }
        }
    }

    private fun openPreview() {
        binding.editorWebView.evaluateJavascript("\$('#summernote').summernote('code')") { content ->
            val originalContent = StringEscapeUtils.unescapeJava(
                content.substring(
                    1,
                    content.length - 1
                )//substring is to remove the start and end quotes.
            )
            startActivity(
                Intent(
                    this,
                    PreviewActivity::class.java
                ).putExtra(
                    PreviewActivity.EXTRA_CONTENT,
                    originalContent
                )
            )
        }
    }
}

