package uk.co.richyhbm.coinbag.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import uk.co.richyhbm.coinbag.databinding.TransactionRowViewBinding
import uk.co.richyhbm.coinbag.databinding.WalletDetailViewBinding
import uk.co.richyhbm.coinbag.models.Transaction
import uk.co.richyhbm.coinbag.models.Wallet
import uk.co.richyhbm.coinbag.utils.AsyncWrap
import uk.co.richyhbm.coinbag.view_holders.TransactionRowViewHolder
import uk.co.richyhbm.coinbag.view_holders.WalletDetailViewHolder
import uk.co.richyhbm.coinbag.view_model.TransactionRowViewModel
import uk.co.richyhbm.coinbag.view_model.WalletDetailViewModel
import android.os.AsyncTask.execute
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.util.Log
import android.widget.Toast
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import uk.co.richyhbm.coinbag.utils.BalanceFetcher


class WalletDetailsAdapter(val wallet: Wallet, var transactions: Array<Transaction>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val WALLET_DETAIL = 1
    val WALLET_TRANSACTION = 2

    override fun getItemCount(): Int {
        return transactions.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        when (position) {
            0 -> return WALLET_DETAIL
            else -> return WALLET_TRANSACTION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        when (viewType) {
            WALLET_DETAIL -> {
                val v1 = WalletDetailViewBinding.inflate(layoutInflater, parent, false)
                return WalletDetailViewHolder(v1)
            }
            else -> {
                val v2 = TransactionRowViewBinding.inflate(layoutInflater, parent, false)
                return TransactionRowViewHolder(v2)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) = when (getItemViewType(position)) {
        WALLET_DETAIL -> {
            val walletDetailVM = WalletDetailViewModel()

            object : AsyncTask<Void, Void, Bitmap>() {
                override fun doInBackground(vararg params: Void): Bitmap? {
                    val width = 1024
                    val result: BitMatrix
                    try {
                        result = MultiFormatWriter().encode(wallet.address, BarcodeFormat.QR_CODE, width, width, null)
                    } catch (iae: IllegalArgumentException) {
                        // Unsupported format
                        return null
                    }
                    val w = result.width
                    val h = result.height
                    val pixels = IntArray(w * h)
                    for (y in 0..h - 1) {
                        val offset = y * w
                        for (x in 0..w - 1) {
                            pixels[offset + x] = if (result.get(x, y)) Color.BLACK else Color.WHITE
                        }
                    }
                    val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
                    bitmap.setPixels(pixels, 0, width, 0, 0, w, h)
                    return bitmap
                }

                override fun onPostExecute(result: Bitmap) {
                    walletDetailVM.walletQr.set(BitmapDrawable(this@WalletDetailsAdapter.context.resources, result))
                }
            }.execute()

            walletDetailVM.walletName.set(wallet.name)
            walletDetailVM.walletAddress.set(wallet.address)
            walletDetailVM.walletBalance.set("Fetching")
            walletDetailVM.walletValue.set("Fetching")

            AsyncWrap( {
                BalanceFetcher.getBalance(wallet.type, wallet.address)
            }, { d ->
                if(d != null) {
                    if(wallet.type.value.get() != 0) {
                        val value = d.times(wallet.type.getValue())
                        walletDetailVM.walletValue.set("%,.2f USD".format(value))
                    }
                    walletDetailVM.walletBalance.set("%,.6f".format(d) + " " + wallet.type.symbol)
                } else {
                    walletDetailVM.walletBalance.set("Failed to fetch")
                    walletDetailVM.walletValue.set("Failed to fetch")
                }
            }, { ex:Exception? ->
                Log.e("Err fetching balance", ex!!.message)
                Toast.makeText(context, "Failed to fetch balance for " + wallet.name, Toast.LENGTH_LONG).show()
            }).execute()

            (holder as WalletDetailViewHolder).bind(walletDetailVM)
        }
        else -> {
            val transactionVM = TransactionRowViewModel()
            (holder as TransactionRowViewHolder).bind(transactionVM)
        }
    }
}