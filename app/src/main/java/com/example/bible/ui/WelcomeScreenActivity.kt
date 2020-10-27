package com.example.bible.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.bible.R
import kotlinx.android.synthetic.main.activity_welcome_screen_fragment.*


class WelcomeScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_welcome_screen_fragment)

        supportActionBar?.hide()

        animateView(image_bible)

        Thread(Runnable {
            try {
                Thread.sleep(6000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }).start()
    }

    private fun animateView(view: View) {
        YoYo.with(Techniques.ZoomIn)
            .duration(3000)
            .playOn(view)
    }
}