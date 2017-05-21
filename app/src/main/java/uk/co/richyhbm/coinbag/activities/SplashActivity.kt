package uk.co.richyhbm.coinbag.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import uk.co.richyhbm.coinbag.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val i = Intent(this@SplashActivity, WelcomeActivity::class.java)
        startActivity(i)
        finish()
    }
}
