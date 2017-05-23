package uk.co.richyhbm.coinbag.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon

object Icons {
    fun getIcon(context: Context, icon: IIcon, color: Int, size: Int): Drawable {
        return IconicsDrawable(context)
                .icon(icon)
                .color(ContextCompat.getColor(context, color))
                .sizeDp(size)
    }
}
