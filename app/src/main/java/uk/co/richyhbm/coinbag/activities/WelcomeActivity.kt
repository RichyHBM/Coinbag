package uk.co.richyhbm.coinbag.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.fragments.WelcomeSlideFragment


class WelcomeActivity : AppIntro() {
    val seenIntro = "seenIntro"

    override fun onCreate(savedInstanceState: Bundle?) {
        val sharedPrefs = getPreferences(Context.MODE_PRIVATE)

        if (sharedPrefs.getBoolean(seenIntro, false)) {
            val i = Intent(this@WelcomeActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }

        super.onCreate(savedInstanceState)

        val backgroundColor: Int = ContextCompat.getColor(this, R.color.grey_50)
        val textColor : Int = ContextCompat.getColor(this, R.color.light_bg_dark_secondary_text)
        val indicatorButtons : Int = ContextCompat.getColor(this, R.color.grey_700)

        addSlide(WelcomeSlideFragment.newInstance(
                getString(R.string.app_name),
                "A read-only crypto-currency wallet manager to track and receive crypto-currency payments",
                R.drawable.coin_bag,
                backgroundColor, textColor, textColor))

        addSlide(WelcomeSlideFragment.newInstance("Coin Bag", "This is an app, 2", R.drawable.coin_bag, backgroundColor, textColor, textColor))
        addSlide(WelcomeSlideFragment.newInstance("Coin Bag", "This is an app, 3", R.drawable.coin_bag, backgroundColor, textColor, textColor))
        addSlide(WelcomeSlideFragment.newInstance("Coin Bag", "This is an app, 4", CommunityMaterial.Icon.cmd_folder, backgroundColor, textColor, textColor))
        addSlide(WelcomeSlideFragment.newInstance("Coin Bag", "This is an app, 5", R.drawable.coin_bag, backgroundColor, textColor, textColor))

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

        val editor = getPreferences(Context.MODE_PRIVATE).edit()
        editor.putBoolean(seenIntro, true)
        editor.apply()
        
        val i = Intent(this@WelcomeActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
    }
}
