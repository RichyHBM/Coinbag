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
                "A read-only crypto-currency wallet watcher to track and receive payments",
                R.drawable.coin_bag,
                backgroundColor, textColor, textColor))

        addSlide(WelcomeSlideFragment.newInstance(
                "Keys",
                "Enter your public keys manually, or scan them via their QR code",
                CommunityMaterial.Icon.cmd_qrcode_scan,
                backgroundColor, textColor, textColor))

        addSlide(WelcomeSlideFragment.newInstance(
                "Secure",
                "Coin bag doesn't have access to your private keys, so it can't lose them",
                CommunityMaterial.Icon.cmd_lock,
                backgroundColor, textColor, textColor))

        addSlide(WelcomeSlideFragment.newInstance(
                "Crypto-coins",
                "Supports many different crypto-coins, with real-time currency values",
                CommunityMaterial.Icon.cmd_coin,
                backgroundColor, textColor, textColor))

        addSlide(WelcomeSlideFragment.newInstance(
                "Keys",
                "Keep track of the value of each of your wallets",
                CommunityMaterial.Icon.cmd_cash,
                backgroundColor, textColor, textColor))

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
        seenAndDone()
    }

    override fun onDonePressed(currentFragment: Fragment) {
        super.onDonePressed(currentFragment)
        seenAndDone()
    }

    override fun onSlideChanged(oldFragment: Fragment?, newFragment: Fragment?) {
        super.onSlideChanged(oldFragment, newFragment)
    }

    fun seenAndDone() {
        val editor = getPreferences(Context.MODE_PRIVATE).edit()
        editor.putBoolean(seenIntro, true)
        editor.apply()

        val i = Intent(this@WelcomeActivity, MainActivity::class.java)
        startActivity(i)
        finish()
    }
}
