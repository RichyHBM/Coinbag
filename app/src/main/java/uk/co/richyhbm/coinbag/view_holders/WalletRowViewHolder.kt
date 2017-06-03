package uk.co.richyhbm.coinbag.view_holders

import android.content.Intent
import android.support.v7.widget.RecyclerView
import uk.co.richyhbm.coinbag.BR
import uk.co.richyhbm.coinbag.activities.EditAccountActivity
import uk.co.richyhbm.coinbag.databinding.WalletRowViewBinding
import uk.co.richyhbm.coinbag.view_model.WalletRowViewModel

class WalletRowViewHolder(val itemBinding: WalletRowViewBinding) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(viewModel: WalletRowViewModel) {
        itemBinding.setVariable(BR.vm, viewModel)
        itemBinding.executePendingBindings()

        itemView.setOnLongClickListener {
            val i = Intent(itemView.context, EditAccountActivity::class.java)
            i.putExtra(EditAccountActivity.EDIT_WALLET_ID, viewModel.walletId.get())
            itemView.context.startActivity(i)
            true
        }
    }
}