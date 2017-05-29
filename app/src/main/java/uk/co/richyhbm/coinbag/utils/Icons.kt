package uk.co.richyhbm.coinbag.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.mikepenz.cryptocoinsicons_typeface_library.CryptocoinsIcons
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon
import uk.co.richyhbm.coinbag.enums.Cryptocoins

object Icons {
    fun getIcon(context: Context, icon: IIcon, color: Int, size: Int): Drawable {
        return IconicsDrawable(context)
                .icon(icon)
                .color(ContextCompat.getColor(context, color))
                .sizeDp(size)
    }

    fun getCryptoIcon(type: Cryptocoins): IIcon {
        when(type) {
            Cryptocoins.Bitcoin -> return CryptocoinsIcons.Icon.cci_BTC
            Cryptocoins.Litecoin -> return CryptocoinsIcons.Icon.cci_LTC
            Cryptocoins.Ethereum -> return CryptocoinsIcons.Icon.cci_ETH_alt
            Cryptocoins.Other -> return CommunityMaterial.Icon.cmd_help_circle
        }
    }
}
