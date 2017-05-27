package uk.co.richyhbm.coinbag.view_model

import android.databinding.ObservableField
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

class ActivityMainViewModel{
    val fabIcon: ObservableField<Drawable> = ObservableField<Drawable>(ColorDrawable(Color.TRANSPARENT))
}