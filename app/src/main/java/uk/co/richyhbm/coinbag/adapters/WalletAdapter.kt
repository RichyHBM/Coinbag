package uk.co.richyhbm.coinbag.adapters

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import uk.co.richyhbm.coinbag.BR
import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.databinding.WalletRowViewBinding
import uk.co.richyhbm.coinbag.models.Wallet
import uk.co.richyhbm.coinbag.utils.AsyncWrap
import uk.co.richyhbm.coinbag.utils.Icons
import uk.co.richyhbm.coinbag.view_model.WalletRowViewModel


class WalletAdapter(var wallets: Array<Wallet>, val context: Context) : RecyclerView.Adapter<WalletAdapter.WalletViewHolder>() {
    override fun getItemCount(): Int {
        return wallets.size
    }

    class WalletViewHolder(val itemBinding: WalletRowViewBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(viewModel: WalletRowViewModel) {
            itemBinding.setVariable(BR.vm, viewModel)
            itemBinding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = WalletRowViewBinding.inflate(layoutInflater, parent, false)
        return WalletViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WalletViewHolder, position: Int) {
        val item = wallets[position]
        val walletVM = WalletRowViewModel()


        walletVM.cryptoName.set(item.name)
        walletVM.cryptoIcon.set(Icons.getIcon(context, Icons.getCryptoIcon(item.type), R.color.grey_700, 36))

        walletVM.cryptoBalance.set("Fetching")
        walletVM.cryptoValue.set("Fetching")
        holder.bind(walletVM)

        AsyncWrap<Unit>( {
            walletVM.cryptoBalance.set("123")
            walletVM.cryptoValue.set("132")
        }, {
            holder.bind(walletVM)
        }, { ex:Exception? ->
            Toast.makeText(context, "Failed: " + ex!!.message, Snackbar.LENGTH_LONG).show()
        }).execute()
    }
}