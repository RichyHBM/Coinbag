package uk.co.richyhbm.coinbag.activities

import android.content.*
import android.net.Uri
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.cryptocurrency_icons_typeface_library.CryptocurrencyIcons
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.enums.Cryptocoins


class AboutActivity : MaterialAboutActivity() {
    fun getIcon(icon: IIcon): IconicsDrawable {
        return IconicsDrawable(this)
                .icon(icon)
                .color(ContextCompat.getColor(this, R.color.grey_700))
                .sizeDp(18)
    }

    override fun getMaterialAboutList(context: Context): MaterialAboutList {
        return MaterialAboutList.Builder()
                .addCard(getAppCardBuilder().build())
                .addCard(getAuthorCardBuilder().build())
                .addCard(getDonateCardBuilder().build())
                .build()
    }

    fun getAppCardBuilder(): MaterialAboutCard.Builder {
        val aboutItem = MaterialAboutTitleItem.Builder()
                .text(R.string.app_name)
                .icon(R.drawable.coin_bag)

        val versionItem = ConvenienceBuilder.createVersionActionItem(this@AboutActivity,
                getIcon(CommunityMaterial.Icon.cmd_information_outline),
                "Version",
                false)

        val changelogItem = MaterialAboutActionItem.Builder()
                .text("Changelog")
                .icon(getIcon(CommunityMaterial.Icon.cmd_history))
                .setOnClickAction {
                    val intent = Intent(this@AboutActivity, ChangeLogActivity::class.java)
                    startActivity(intent)
                }

        val licensesItem = MaterialAboutActionItem.Builder()
                .text("Licenses")
                .icon(getIcon(CommunityMaterial.Icon.cmd_book))
                .setOnClickAction {
                    val intent = Intent(this@AboutActivity, LicensesActivity::class.java)
                    startActivity(intent)
                }

        val appCardBuilder: MaterialAboutCard.Builder = MaterialAboutCard.Builder()
                .addItem(aboutItem.build())
                .addItem(versionItem)
                .addItem(changelogItem.build())
                .addItem(licensesItem.build())

        return appCardBuilder
    }

    fun getAuthorCardBuilder(): MaterialAboutCard.Builder {
        val authorCardBuilder: MaterialAboutCard.Builder = MaterialAboutCard.Builder()
                .title("Author")

        val nameItem = MaterialAboutActionItem.Builder()
                .text("Richy HBM")
                .icon(getIcon(CommunityMaterial.Icon.cmd_web))
                .setOnClickAction {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.richyhbm.co.uk"))
                    startActivity(browserIntent)
                }

        val githubItem = MaterialAboutActionItem.Builder()
                .text("Github")
                .icon(getIcon(CommunityMaterial.Icon.cmd_github_circle))
                .setOnClickAction {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.github.com/richyhbm"))
                    startActivity(browserIntent)
                }

        val twitterItem = MaterialAboutActionItem.Builder()
                .text("Twitter")
                .icon(getIcon(CommunityMaterial.Icon.cmd_twitter))
                .setOnClickAction {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/richyhbm"))
                    startActivity(browserIntent)
                }

        authorCardBuilder
                .addItem(nameItem.build())
                .addItem(githubItem.build())
                .addItem(twitterItem.build())

        return authorCardBuilder
    }

    fun tryDonateIntent(crypto: Cryptocoins, address: String) {
        try {
            val uriIntent = Intent(Intent.ACTION_VIEW, Uri.parse(crypto.getUriString(address)))
            startActivity(uriIntent)
        } catch(e: ActivityNotFoundException) {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Donation Address", address)
            clipboard.primaryClip = clip
            Snackbar.make(currentFocus, crypto.getFriendlyName() + " donation address copied to clipboard!", Snackbar.LENGTH_LONG).show()
        }
    }

    fun getDonateCardBuilder(): MaterialAboutCard.Builder {
        val donateCardBuilder: MaterialAboutCard.Builder = MaterialAboutCard.Builder()
                .title("Donate")

        val bitcoinItem = MaterialAboutActionItem.Builder()
                .text(Cryptocoins.Bitcoin.getFriendlyName())
                .icon(getIcon(CryptocurrencyIcons.Icon.cci_btc))
                .setOnClickAction {
                    val address = getString(R.string.donate_btc)
                    tryDonateIntent(Cryptocoins.Bitcoin, address)
                }

        val ethereumItem = MaterialAboutActionItem.Builder()
                .text(Cryptocoins.Ethereum.getFriendlyName())
                .icon(getIcon(CryptocurrencyIcons.Icon.cci_eth))
                .setOnClickAction {
                    val address = getString(R.string.donate_eth)
                    tryDonateIntent(Cryptocoins.Ethereum, address)
                }

        val litecoinItem = MaterialAboutActionItem.Builder()
                .text(Cryptocoins.Litecoin.getFriendlyName())
                .icon(getIcon(CryptocurrencyIcons.Icon.cci_ltc))
                .setOnClickAction {
                    val address = getString(R.string.donate_ltc)
                    tryDonateIntent(Cryptocoins.Litecoin, address)
                }

        donateCardBuilder.addItem(bitcoinItem.build())
                .addItem(ethereumItem.build())
                .addItem(litecoinItem.build())

        return donateCardBuilder
    }

    override fun getActivityTitle(): CharSequence {
        return getString(R.string.mal_title_about)
    }

}
