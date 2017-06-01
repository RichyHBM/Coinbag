package uk.co.richyhbm.coinbag.view_holders

import android.support.v7.widget.RecyclerView
import uk.co.richyhbm.coinbag.BR
import uk.co.richyhbm.coinbag.databinding.WalletRowViewBinding
import uk.co.richyhbm.coinbag.view_model.WalletRowViewModel

class WalletViewHolder(val itemBinding: WalletRowViewBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(viewModel: WalletRowViewModel) {
        itemBinding.setVariable(BR.vm, viewModel)
        itemBinding.executePendingBindings()
    }
}