package uk.co.richyhbm.coinbag.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import uk.co.richyhbm.coinbag.R

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adsElement = Element()
        adsElement.title = "Test"
        adsElement.setOnClickListener({
            _: View? -> Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
        })

        val aboutPage = AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.coin_bag)
                .addItem(adsElement)
                .create()

        setContentView(aboutPage)
    }
}
