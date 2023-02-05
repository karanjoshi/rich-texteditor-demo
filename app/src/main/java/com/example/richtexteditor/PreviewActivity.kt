package com.example.richtexteditor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.richtexteditor.databinding.ActivityPreviewBinding

class PreviewActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CONTENT: String = "content"
    }

    lateinit var binding: ActivityPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.webViewPreview.loadData(
            intent.getStringExtra(EXTRA_CONTENT) ?: "Unable to preview content!",
            "text/html", Charsets.UTF_32.name()
        )
    }
}