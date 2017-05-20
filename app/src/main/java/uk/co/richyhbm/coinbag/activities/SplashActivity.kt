package uk.co.richyhbm.coinbag.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import uk.co.richyhbm.coinbag.R

class SplashActivity : AppCompatActivity() {
    val REQUEST_CODE_INTRO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val i = Intent(this@SplashActivity, WelcomeActivity::class.java)
        startActivityForResult(i, REQUEST_CODE_INTRO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_INTRO) {
            if (resultCode == Activity.RESULT_OK) {
                val i = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(i)
            }
            finish()
        }
    }
}
