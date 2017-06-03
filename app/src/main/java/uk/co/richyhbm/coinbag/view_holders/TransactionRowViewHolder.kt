package uk.co.richyhbm.coinbag.view_holders

import android.content.Intent
import android.support.v7.widget.RecyclerView
import uk.co.richyhbm.coinbag.BR
import uk.co.richyhbm.coinbag.activities.EditAccountActivity
import uk.co.richyhbm.coinbag.activities.WalletDetailsActivity
import uk.co.richyhbm.coinbag.databinding.TransactionRowViewBinding
import uk.co.richyhbm.coinbag.databinding.WalletRowViewBinding
import uk.co.richyhbm.coinbag.view_model.TransactionRowViewModel
import uk.co.richyhbm.coinbag.view_model.WalletRowViewModel

class TransactionRowViewHolder(val itemBinding: TransactionRowViewBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(viewModel: TransactionRowViewModel) {
        itemBinding.setVariable(BR.vm, viewModel)
        itemBinding.executePendingBindings()
    }
}