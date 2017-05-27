package uk.co.richyhbm.coinbag.view_model

import android.databinding.ObservableField
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import uk.co.richyhbm.coinbag.enums.Cryptocoins

class AddAccountViewModel {
    val fabIcon = ObservableField<Drawable>(ColorDrawable(Color.TRANSPARENT))
    val cryptoIcon = ObservableField<Drawable>(ColorDrawable(Color.TRANSPARENT))
    val cryptoIconVisible = ObservableField<Boolean>(true)
    val address = ObservableField<String>("")
    val supportedCryptoList = ObservableField<Array<String>>()
    val spinnerSelectedIdx = ObservableField<Int>(0)
}