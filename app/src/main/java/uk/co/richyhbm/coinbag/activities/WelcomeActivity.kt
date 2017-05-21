package uk.co.richyhbm.coinbag.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import uk.co.richyhbm.coinbag.R


class WelcomeActivity : AppIntro() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val backgroundColor: Int = ContextCompat.getColor(this, R.color.grey_50)
        val textColor : Int = ContextCompat.getColor(this, R.color.light_bg_dark_secondary_text)
        val indicatorButtons : Int = ContextCompat.getColor(this, R.color.grey_700)

        addSlide(AppIntroFragment.newInstance("Coin Bag", "This is an app, 1", R.drawable.coin_bag, backgroundColor, textColor, textColor))
        addSlide(AppIntroFragment.newInstance("Coin Bag", "This is an app, 2", R.drawable.coin_bag, backgroundColor, textColor, textColor))
        addSlide(AppIntroFragment.newInstance("Coin Bag", "This is an app, 3", R.drawable.coin_bag, backgroundColor, textColor, textColor))
        addSlide(AppIntroFragment.newInstance("Coin Bag", "This is an app, 4", R.drawable.coin_bag, backgroundColor, textColor, textColor))
        addSlide(AppIntroFragment.newInstance("Coin Bag", "This is an app, 5", R.drawable.coin_bag, backgroundColor, textColor, textColor))

        setNextArrowColor(indicatorButtons)
        setColorSkipButton(indicatorButtons)
        setColorDoneText(indicatorButtons)

        setColorTransitionsEnabled(true)
        setIndicatorColor(indicatorButtons, ContextCompat.getColor(this, R.color.grey_500))

        setBarColor(backgroundColor)
        setSeparatorColor(backgroundColor)
    }

    override fun onSkipPressed(currentFragment: Fragment) {
        super.onSkipPressed(currentFragment)
        finish()
    }

    override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
        val i = Intent(this@WelcomeActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
    }
}
