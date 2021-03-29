package com.example.submissionwahyu.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.submissionwahyu.R

class SplashscreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Handler(Looper.getMainLooper()).postDelayed(object : Runnable {
            override fun run() {
                startActivity(Intent(this@SplashscreenActivity, MainActivity::class.java))
                finish()
            }
        },5000)
    }
}