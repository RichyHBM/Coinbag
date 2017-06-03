package uk.co.richyhbm.coinbag.view_model

import android.databinding.ObservableField
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable

class WalletDetailViewModel {
    val walletQr = ObservableField<Drawable>(ColorDrawable(Color.TRANSPARENT))
    val walletName = ObservableField<String>("")
    val walletAddress = ObservableField<String>("")
    val walletBalance = ObservableField<String>("")
    val walletValue = ObservableField<String>("")

}