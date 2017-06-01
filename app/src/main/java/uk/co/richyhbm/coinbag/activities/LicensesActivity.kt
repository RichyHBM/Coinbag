package uk.co.richyhbm.coinbag.activities

import android.content.Context
import android.graphics.drawable.Drawable
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.danielstone.materialaboutlibrary.util.OpenSourceLicense
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.utils.Icons

class LicensesActivity : MaterialAboutActivity() {
    fun createLicenseCard(context:Context, icon: Drawable, libraryTitle: String, year: String, name: String, license: Int ): MaterialAboutCard {
        val licenseItem = MaterialAboutActionItem.Builder()
                .icon(icon)
                .setIconGravity(0)
                .text(libraryTitle).subText(String.format(context.getString(license), year, name)).build()

        return MaterialAboutCard.Builder().addItem(licenseItem).build()
    }

    override fun getMaterialAboutList(context: Context): MaterialAboutList {

        val icon = Icons.getIcon(context, CommunityMaterial.Icon.cmd_book, R.color.grey_700, 18)

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
                "Cryptocoins icons", "2017", "Martin Allien",
                OpenSourceLicense.MIT.resourceId)

        val communityMaterialIconsLicense = createLicenseCard(context,
                icon,
                "MaterialDesign Icons", "2014", "Austin Andrews (http://materialdesignicons.com/)",
                R.string.sil_open_font_license)

        val appIntroLicense = createLicenseCard(context,
                icon,
                "AppIntro Library", "2015", "Paolo Rotolo, Maximilian Narr",
                OpenSourceLicense.APACHE_2.resourceId)

        val zxingEmbeddedLicense = createLicenseCard(context,
                icon,
                "ZXing Android Embedded", "2012", "ZXing authors, Journey Mobile",
                OpenSourceLicense.APACHE_2.resourceId)

        val realmLicense = createLicenseCard(context,
                icon,
                "Realm Java Database", "2012", "Realm (http://realm.io)",
                OpenSourceLicense.APACHE_2.resourceId)

        val mPAndroidChartLicense = createLicenseCard(context,
                icon,
                "MPAndroidChart", "2016", "Philipp Jahoda",
                OpenSourceLicense.APACHE_2.resourceId)

        return MaterialAboutList(
                retrofitLicenseItem,
                materialAboutLibraryLicense,
                androidIconicsLicense,
                googleMaterialTypefaceLicense,
                logoLicense,
                cryptoIconsLicense,
                communityMaterialIconsLicense,
                appIntroLicense,
                zxingEmbeddedLicense,
                realmLicense,
                mPAndroidChartLicense)
    }

    override fun getActivityTitle(): CharSequence {
        return getString(R.string._3rd_party)
    }
}
