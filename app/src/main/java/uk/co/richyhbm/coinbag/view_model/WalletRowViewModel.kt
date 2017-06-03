package uk.co.richyhbm.coinbag.view_model

import android.databinding.ObservableField
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

class WalletRowViewModel {
    val walletId = ObservableField<Int>(0)
    val cryptoIcon = ObservableField<Drawable>(ColorDrawable(Color.TRANSPARENT))
    val cryptoName = ObservableField<String>("")
    val cryptoBalance = ObservableField<String>("")
    val cryptoValue = ObservableField<String>("")
    val cryptoAddress = ObservableField<String>("")
}