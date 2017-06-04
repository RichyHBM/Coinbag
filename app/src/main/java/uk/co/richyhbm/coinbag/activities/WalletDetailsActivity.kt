package uk.co.richyhbm.coinbag.activities

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem

import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.adapters.WalletDetailsAdapter
import uk.co.richyhbm.coinbag.enums.Cryptocoins
import uk.co.richyhbm.coinbag.models.Wallet

class WalletDetailsActivity : AppCompatActivity() {
    companion object {
        val WALLET_ID = "WalletId"
        val WALLET_NAME = "WalletName"
        val WALLET_ADDRESS = "WalletAddress"
        val WALLET_TYPE = "WalletType"
    }

    var recyclerView: RecyclerView? = null
    var refreshLayout: SwipeRefreshLayout? = null
    var recyclerListAdapter: WalletDetailsAdapter? = null
    var wallet:Wallet? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet_details)

        wallet = Wallet(
                this.intent.extras.get(WALLET_ID) as Int,
                this.intent.extras.get(WALLET_NAME).toString(),
                this.intent.extras.get(WALLET_ADDRESS).toString(),
                Cryptocoins.valueOf(this.intent.extras.get(WALLET_TYPE).toString()))

        title = wallet!!.name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        refreshLayout = findViewById(R.id.wallet_details_swipe_refresh_layout) as SwipeRefreshLayout
        refreshLayout!!.setOnRefreshListener {
            updateAdapter()
        }

        recyclerView = findViewById(R.id.wallet_details_recycler_view) as RecyclerView
        recyclerView!!.setHasFixedSize(true)

        recyclerListAdapter = WalletDetailsAdapter(wallet!!, arrayOf(), applicationContext)
        recyclerView!!.adapter = recyclerListAdapter

        val layoutManager = LinearLayoutManager(this@WalletDetailsActivity)
        recyclerView!!.layoutManager = layoutManager

        updateAdapter()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun updateAdapter() {
        val asyncTask = object : AsyncTask<Void, Void, Unit>() {
            override fun doInBackground(vararg params: Void):Unit {

            }

            override fun onPostExecute(u: Unit) {
                recyclerView!!.adapter.notifyDataSetChanged()
                refreshLayout!!.isRefreshing = false
            }
        }

        asyncTask.execute()
    }
}
