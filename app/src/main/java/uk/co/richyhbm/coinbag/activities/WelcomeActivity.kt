package uk.co.richyhbm.coinbag.activities

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import uk.co.richyhbm.coinbag.R

class WelcomeActivity : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val color: Int = Color.parseColor(getResources().getString(R.color.colorAccent))

        addSlide(AppIntroFragment.newInstance("Coin Bag", "This is an app, 1", R.drawable.coin_bag, color))
        addSlide(AppIntroFragment.newInstance("Coin Bag", "This is an app, 2", R.drawable.coin_bag, color))
        addSlide(AppIntroFragment.newInstance("Coin Bag", "This is an app, 3", R.drawable.coin_bag, color))
        addSlide(AppIntroFragment.newInstance("Coin Bag", "This is an app, 4", R.drawable.coin_bag, color))
        addSlide(AppIntroFragment.newInstance("Coin Bag", "This is an app, 5", R.drawable.coin_bag, color))
    }

    override fun onSkipPressed(currentFragment: Fragment) {
        super.onSkipPressed(currentFragment)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
        val i = Intent(this@WelcomeActivity, AboutActivity::class.java)
        startActivity(i)
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
    }
}
