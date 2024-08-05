package com.hank.dev

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, NewsFragment())
            commit()
        }

    }
}