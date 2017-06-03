package uk.co.richyhbm.coinbag.adapters

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.github.mikephil.charting.charts.PieChart
import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.databinding.WalletRowViewBinding
import uk.co.richyhbm.coinbag.databinding.WalletsSummaryRowBinding
import uk.co.richyhbm.coinbag.models.Wallet
import uk.co.richyhbm.coinbag.utils.AsyncWrap
import uk.co.richyhbm.coinbag.utils.BalanceFetcher
import uk.co.richyhbm.coinbag.utils.Icons
import uk.co.richyhbm.coinbag.utils.WalletSummaryData
import uk.co.richyhbm.coinbag.view_holders.WalletSummaryViewHolder
import uk.co.richyhbm.coinbag.view_holders.WalletRowViewHolder
import uk.co.richyhbm.coinbag.view_model.WalletRowViewModel

class WalletListAdapter(var wallets: Array<Wallet>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val WALLET_SUMMARY = 1
    val WALLET_ROW = 2
    var pieChart:PieChart? = null
    val walletData = WalletSummaryData()

    //Add 1 to the wallet count to account for the summary
    //Should the summary be passed as part of the array? ATM it is just built in here
    //If it were in the array it would mean more than 1 can be passed in, but this way doesn't feel very MVVM
    override fun getItemCount(): Int {
        if(wallets.isEmpty()) return 0
        else return wallets.size + 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        when (viewType) {
            WALLET_SUMMARY -> {
                val v1 = WalletsSummaryRowBinding.inflate(layoutInflater, parent, false)

                return WalletSummaryViewHolder(v1)
            }
            else -> {
                val v2 = WalletRowViewBinding.inflate(layoutInflater, parent, false)
                return WalletRowViewHolder(v2)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> return WALLET_SUMMARY
            else -> return WALLET_ROW
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (getItemViewType(position)) {
        WALLET_SUMMARY -> {
            pieChart = holder.itemView.findViewById(R.id.pie_chart) as PieChart

            walletData.reset()
            (holder as WalletSummaryViewHolder).bind(walletData.summaryVM)
        }
        else -> {
            val walletVM = buildWalletRow(position - 1)
            (holder as WalletRowViewHolder).bind(walletVM)
        }
    }

    fun buildWalletRow(pos: Int): WalletRowViewModel {
        val item = wallets[pos]
        val walletVM = WalletRowViewModel()

        walletVM.walletId.set(item.id)
        walletVM.cryptoName.set(item.name)
        walletVM.cryptoIcon.set(Icons.getIcon(context, Icons.getCryptoIcon(item.type), R.color.grey_700, 36))
        walletVM.cryptoAddress.set(item.address.substring(0, 6) + "..." + item.address.takeLast(6))
        walletVM.cryptoBalance.set("Fetching")
        walletVM.cryptoValue.set("Fetching")

        AsyncWrap( {
            BalanceFetcher.getBalance(item.type, item.address)
        }, { d ->
            if(d != null) {
                if(item.type.value.get() != 0) {
                    val value = d.times(item.type.getValue())
                    walletData.addToTotalValue(value)
                    walletData.addNewTypeValuePair(Pair(item.type, value))
                    val totalUsd = "%,.2f USD".format(walletData.getTotalValue())
                    walletVM.cryptoValue.set("%,.2f USD".format(value))
                    walletData.summaryVM.totalValue.set(totalUsd)
                }
                walletVM.cryptoBalance.set("%,.6f".format(d) + " " + item.type.symbol)

            } else {
                walletVM.cryptoBalance.set("Failed to fetch")
                walletVM.cryptoValue.set("Failed to fetch")
            }

            if(pos + 2 == getItemCount() && pieChart != null) {
                walletData.buildChart(pieChart!!).invalidate()
            }

        }, { ex:Exception? ->
            Log.e("Err fetching balance", ex!!.message)
            Toast.makeText(context, "Failed to fetch balance for " + item.name, Toast.LENGTH_LONG).show()
            if(pos + 2 == getItemCount() && pieChart != null) {
                walletData.buildChart(pieChart!!).invalidate()
            }
        }).execute()

        return walletVM
    }
}