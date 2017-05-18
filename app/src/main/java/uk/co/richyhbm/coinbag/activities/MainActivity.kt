package uk.co.richyhbm.coinbag.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import uk.co.richyhbm.coinbag.R
import android.content.Intent



class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val i = Intent(this@MainActivity, WelcomeActivity::class.java)
        startActivity(i)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}
