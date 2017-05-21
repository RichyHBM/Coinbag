package uk.co.richyhbm.coinbag.activities

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
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
                .icon(getIcon(CommunityMaterial.Icon.cmd_account))

        val websiteItem = MaterialAboutActionItem.Builder()
                .text("Website")
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
                .addItem(websiteItem.build())
                .addItem(githubItem.build())
                .addItem(twitterItem.build())

        return authorCardBuilder
    }

    override fun getActivityTitle(): CharSequence {
        return getString(R.string.mal_title_about)
    }

}
