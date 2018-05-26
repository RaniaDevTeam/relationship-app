package com.rania.relationship_app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView

class ChapterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chapter)
        val cardNum = intent.getIntExtra(Keys.CARD_NUM, 22)
        findViewById<WebView>(R.id.card_num).apply {
            loadUrl("file:///android_asset/$cardNum.html")
        }

    }
}