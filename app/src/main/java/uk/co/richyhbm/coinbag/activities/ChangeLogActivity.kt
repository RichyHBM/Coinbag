package uk.co.richyhbm.coinbag.activities

import android.content.Context
import android.support.v4.content.ContextCompat
import com.danielstone.materialaboutlibrary.MaterialAboutActivity
import com.danielstone.materialaboutlibrary.items.MaterialAboutActionItem
import com.danielstone.materialaboutlibrary.model.MaterialAboutCard
import com.danielstone.materialaboutlibrary.model.MaterialAboutList
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.utils.Icons

class ChangeLogActivity : MaterialAboutActivity() {
    fun createChangelogCard(context: Context, version: String, changes: Array<String>): MaterialAboutCard {
        val cardBuilder = MaterialAboutCard.Builder()

        val versionItem =  MaterialAboutActionItem.Builder()
                .icon(Icons.getIcon(this, CommunityMaterial.Icon.cmd_history, R.color.grey_700, 18))
                .setIconGravity(0)
                .text(version)

        val stringBuilder = StringBuilder()
        for (change in changes) {
            stringBuilder.appendln("- " + change)
        }

        cardBuilder.addItem(versionItem.subText(stringBuilder).build())

        return cardBuilder.build()
    }

    override fun getMaterialAboutList(context: Context): MaterialAboutList {

        return MaterialAboutList(
                createChangelogCard(context, "1.0.0", v1_0_0)
        )
    }

    override fun getActivityTitle(): CharSequence {
        return getString(R.string.change_log)
    }

    val v1_0_0 = arrayOf("Support BTC", "Support ETH", "Initial app release")
}
