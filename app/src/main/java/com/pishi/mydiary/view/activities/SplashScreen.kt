package com.pishi.mydiary.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pishi.mydiary.R
import com.pishi.mydiary.databinding.ActivitySplashScreenBinding

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(splashScreenBinding.root)
    }
}