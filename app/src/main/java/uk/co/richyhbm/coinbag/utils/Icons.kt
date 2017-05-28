package uk.co.richyhbm.coinbag.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.mikepenz.cryptocurrency_icons_typeface_library.CryptocurrencyIcons
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
            Cryptocoins.Bitcoin -> return CryptocurrencyIcons.Icon.cci_btc
            Cryptocoins.Litecoin -> return CryptocurrencyIcons.Icon.cci_ltc
            Cryptocoins.Ethereum -> return CryptocurrencyIcons.Icon.cci_eth
            Cryptocoins.Other -> return CommunityMaterial.Icon.cmd_help
        }
    }
}
