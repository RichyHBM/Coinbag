package uk.co.richyhbm.coinbag.activities

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element
import uk.co.richyhbm.coinbag.R


class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val apacheLicenseWebView: WebView = WebView(this)
        val mitLicenseWebView: WebView = WebView(this)

        val licenseDialog: AlertDialog.Builder = AlertDialog.Builder(this)

        val appIntroElement = Element()
        val retrofitElement = Element()
        val aboutPageElement = Element()

        val removeWebView = { v: View ->
            val parent = v.parent
            if(parent is ViewGroup) {
                parent.removeAllViews()
            }
        }

        val showLicenseView = { str:Int, v: View ->
            licenseDialog
                    .setTitle(getString(str))
                    .setView(v)
                    .setNeutralButton(getString(R.string.ok), { _, _ -> removeWebView(v) })
                    .setOnDismissListener({ removeWebView(v) })
                    .show()
        }

        apacheLicenseWebView.loadUrl("file:///android_asset/apache-v2.html")
        mitLicenseWebView.loadUrl("file:///android_asset/mit.html")

        appIntroElement.title = getString(R.string.app_intro)
        retrofitElement.title = getString(R.string.retrofit)
        aboutPageElement.title = getString(R.string.about_page_lib)

        appIntroElement.setOnClickListener({ _: View? -> showLicenseView(R.string.apache_v2, apacheLicenseWebView) })
        retrofitElement.setOnClickListener({ _: View? -> showLicenseView(R.string.apache_v2, apacheLicenseWebView) })
        aboutPageElement.setOnClickListener({ _: View? -> showLicenseView(R.string.mit, mitLicenseWebView) })

        val aboutPage = AboutPage(this)
                .isRTL(false)
                .setImage(R.drawable.coin_bag)
                .addGroup(getString(R.string._3rd_party))
                .addItem(appIntroElement)
                .addItem(retrofitElement)
                .addItem(aboutPageElement)
                .create()

        setContentView(aboutPage)
    }
}
