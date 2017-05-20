package uk.co.richyhbm.coinbag.activities

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutTitleItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import uk.co.richyhbm.coinbag.R
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import android.support.v4.content.ContextCompat
import com.mikepenz.iconics.IconicsDrawable
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import android.content.pm.PackageManager
import com.mikepenz.google_material_typeface_library.GoogleMaterial
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import com.danielstone.materialaboutlibrary.items.MaterialAboutItemOnClickAction
import com.mikepenz.iconics.typeface.IIcon


class AboutActivity : MaterialAboutActivity() {
    fun getIcon(icon: IIcon): IconicsDrawable {
        return IconicsDrawable(this)
                .icon(icon)
                .color(ContextCompat.getColor(this, R.color.grey_700))
                .sizeDp(18)
    }

    override fun getMaterialAboutList(context: Context): MaterialAboutList {
        val aboutItem = MaterialAboutTitleItem.Builder()
                .text(R.string.app_name)
                .icon(R.drawable.coin_bag)

        val versionItem = ConvenienceBuilder.createVersionActionItem(context,
                getIcon(GoogleMaterial.Icon.gmd_info_outline),
                "Version",
                false)

        val changelogItem = MaterialAboutActionItem.Builder()
                .text("Changelog")
                .icon(getIcon(GoogleMaterial.Icon.gmd_history))

        val licensesItem = MaterialAboutActionItem.Builder()
                .text("Licenses")
                .icon(getIcon(GoogleMaterial.Icon.gmd_book))
                .setOnClickAction {
                    val intent = Intent(this@AboutActivity, LicensesActivity::class.java)
                    startActivity(intent)
                }


        val appCardBuilder: MaterialAboutCard.Builder = MaterialAboutCard.Builder()
                .addItem(aboutItem.build())
                .addItem(versionItem)
                .addItem(changelogItem.build())
                .addItem(licensesItem.build())


        return MaterialAboutList.Builder()
                .addCard(appCardBuilder.build())
                .build()
    }

    override fun getActivityTitle(): CharSequence {
        return getString(R.string.mal_title_about)
    }

}
