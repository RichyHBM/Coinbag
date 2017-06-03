package uk.co.richyhbm.coinbag.view_holders

import android.support.v7.widget.RecyclerView
import uk.co.richyhbm.coinbag.BR
import uk.co.richyhbm.coinbag.databinding.WalletDetailViewBinding
import uk.co.richyhbm.coinbag.view_model.WalletDetailViewModel


class WalletDetailViewHolder(val itemBinding: WalletDetailViewBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(viewModel: WalletDetailViewModel) {
        itemBinding.setVariable(BR.vm, viewModel)
        itemBinding.executePendingBindings()
    }
}