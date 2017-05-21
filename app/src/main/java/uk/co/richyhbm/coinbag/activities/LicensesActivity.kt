package uk.co.richyhbm.coinbag.activities

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import uk.co.richyhbm.coinbag.R
import com.danielstone.materialaboutlibrary.util.OpenSourceLicense
import android.support.v4.content.ContextCompat
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.danielstone.materialaboutlibrary.ConvenienceBuilder
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard


class LicensesActivity : MaterialAboutActivity() {
    fun createLicenseCard(context:Context, icon: IconicsDrawable, libraryTitle: String, year: String, name: String, license: Int ): MaterialAboutCard {
        val licenseItem = MaterialAboutActionItem.Builder()
                .icon(icon)
                .setIconGravity(0)
                .text(libraryTitle).subText(String.format(context.getString(license), year, name)).build()

        return MaterialAboutCard.Builder().addItem(licenseItem).build()
    }

    override fun getMaterialAboutList(context: Context): MaterialAboutList {

        val icon = IconicsDrawable(context)
                .icon(CommunityMaterial.Icon.cmd_book)
                .color(ContextCompat.getColor(context, R.color.grey_700))
                .sizeDp(18)

        val retrofitLicenseItem = createLicenseCard(context,
                icon,
                "Retrofit", "2013", "Square, Inc",
                OpenSourceLicense.APACHE_2.resourceId)

        val materialAboutLibraryLicense = createLicenseCard(context,
                icon,
                "material-about-library", "2016", "Daniel Stone",
                OpenSourceLicense.APACHE_2.resourceId)

        val androidIconicsLicense = createLicenseCard(context,
                icon,
                "Android-Iconics", "2016", "Mike Penz",
                OpenSourceLicense.APACHE_2.resourceId)

        val googleMaterialTypefaceLicense = createLicenseCard(context,
                icon,
                "material-design-icons", "2014", "Google",
                OpenSourceLicense.APACHE_2.resourceId)

        val logoLicense = createLicenseCard(context,
                icon,
                "Coin Bag Icon", "Noun Project", "Karthick Nagarajan",
                R.string.cc0_license)

        val cryptoIconsLicense = createLicenseCard(context,
                icon,
                "cryptocurrency-icons", "2017", "Coinranking",
                OpenSourceLicense.MIT.resourceId)

        val communityMaterialIconsLicense = createLicenseCard(context,
                icon,
                "MaterialDesign", "2014", "Austin Andrews (http://materialdesignicons.com/)",
                R.string.sil_open_font_license)

        val appIntroLicense = createLicenseCard(context,
                icon,
                "AppIntro Library", "2015", "Paolo Rotolo, Maximilian Narr",
                OpenSourceLicense.APACHE_2.resourceId)

        return MaterialAboutList(
                retrofitLicenseItem,
                materialAboutLibraryLicense,
                androidIconicsLicense,
                googleMaterialTypefaceLicense,
                logoLicense,
                cryptoIconsLicense,
                communityMaterialIconsLicense,
                appIntroLicense)
    }

    override fun getActivityTitle(): CharSequence {
        return getString(R.string._3rd_party)
    }
}
