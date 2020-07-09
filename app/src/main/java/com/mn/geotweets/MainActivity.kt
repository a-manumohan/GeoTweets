package com.mn.geotweets

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}