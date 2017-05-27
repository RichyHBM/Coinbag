package uk.co.richyhbm.coinbag.view_model

import android.app.Activity
import android.content.Intent
import android.databinding.ObservableField
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import uk.co.richyhbm.coinbag.activities.AddAccountActivity

class ActivityMainViewModel{
    val fabIcon: ObservableField<Drawable> = ObservableField<Drawable>(ColorDrawable(Color.TRANSPARENT))
}