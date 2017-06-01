package uk.co.richyhbm.coinbag.adapters

import android.content.Context
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import com.github.mikephil.charting.animation.Easing
import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.databinding.WalletRowViewBinding
import uk.co.richyhbm.coinbag.databinding.WalletsSummaryRowBinding
import uk.co.richyhbm.coinbag.models.Wallet
import uk.co.richyhbm.coinbag.utils.AsyncWrap
import uk.co.richyhbm.coinbag.utils.Icons
import uk.co.richyhbm.coinbag.view_holders.WalletSummaryViewHolder
import uk.co.richyhbm.coinbag.view_holders.WalletViewHolder
import uk.co.richyhbm.coinbag.view_model.WalletRowViewModel
import uk.co.richyhbm.coinbag.view_model.WalletSummaryViewModel
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate


class WalletAdapter(var wallets: Array<Wallet>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val WALLET_SUMMARY = 1
    val WALLET_ROW = 2

    //Add 1 to the wallet count to account for the summary
    //Should the summary be passed as part of the array? ATM it is just built in here
    //If it were in the array it would mean more than 1 can be passed in, but this way doesn't feel very MVVM
    override fun getItemCount(): Int {
        return wallets.size + 1
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
                return WalletViewHolder(v2)
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
            val walletVM = buildSummary(holder as WalletSummaryViewHolder)
            (holder as WalletSummaryViewHolder).bind(walletVM)
        }
        else -> {
            val walletVM = buildWalletRow(position - 1)
            (holder as WalletViewHolder).bind(walletVM)
        }
    }

    fun buildSummary(viewHolder: WalletSummaryViewHolder): WalletSummaryViewModel {
        val summaryVM = WalletSummaryViewModel()

        val pieChart = viewHolder.itemView.findViewById(R.id.pie_chart) as PieChart


        val cryptoCountMapmap = wallets.map {wallet: Wallet ->
            wallet.type to wallets.count { it.type == wallet.type }
        }.distinctBy { it.first }

        val entries = cryptoCountMapmap.map {
            PieEntry(it.second.toFloat(), it.first.getFriendlyName())
        }

        val dataSet = PieDataSet(entries.toMutableList(), "")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 5f
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toMutableList()
        dataSet.valueFormatter = PercentFormatter()
        dataSet.yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
        dataSet.valueTextColor = Color.BLACK

        val data = PieData(dataSet)
        pieChart.data = data
        //pieChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        //pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
        //pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.setDrawInside(false)
        pieChart.legend.isEnabled = true
        pieChart.description.isEnabled = false
        pieChart.setUsePercentValues(true)
        pieChart.isRotationEnabled = false
        pieChart.animateY(600, Easing.EasingOption.EaseInOutQuad)
        pieChart.setDrawEntryLabels(false)
        pieChart.setEntryLabelColor(Color.BLACK)

        pieChart.invalidate()

        return summaryVM
    }

    fun buildWalletRow(pos: Int): WalletRowViewModel {
        val item = wallets[pos]
        val walletVM = WalletRowViewModel()

        walletVM.cryptoName.set(item.name)
        walletVM.cryptoIcon.set(Icons.getIcon(context, Icons.getCryptoIcon(item.type), R.color.grey_700, 36))

        walletVM.cryptoBalance.set("Fetching")
        walletVM.cryptoValue.set("Fetching")

        AsyncWrap( {
            walletVM.cryptoBalance.set("123")
            walletVM.cryptoValue.set("132")
        }, {}, { ex:Exception? ->
            Toast.makeText(context, "Failed: " + ex!!.message, Snackbar.LENGTH_LONG).show()
        }).execute()

        return walletVM
    }
}