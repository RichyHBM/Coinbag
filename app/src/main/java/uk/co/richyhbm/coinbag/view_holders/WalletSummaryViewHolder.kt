package uk.co.richyhbm.coinbag.view_holders

import android.support.v7.widget.RecyclerView
import uk.co.richyhbm.coinbag.BR
import uk.co.richyhbm.coinbag.databinding.WalletsSummaryRowBinding
import uk.co.richyhbm.coinbag.view_model.WalletSummaryViewModel


class WalletSummaryViewHolder(val itemBinding: WalletsSummaryRowBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(viewModel: WalletSummaryViewModel) {
        itemBinding.setVariable(BR.vm, viewModel)
        itemBinding.executePendingBindings()
    }
}