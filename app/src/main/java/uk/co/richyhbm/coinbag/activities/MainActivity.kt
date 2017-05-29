package uk.co.richyhbm.coinbag.activities

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import uk.co.richyhbm.coinbag.BR
import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.databinding.ActivityMainBinding
import uk.co.richyhbm.coinbag.utils.Icons
import uk.co.richyhbm.coinbag.view_model.ActivityMainViewModel
import android.support.v7.widget.RecyclerView
import uk.co.richyhbm.coinbag.MyApp
import uk.co.richyhbm.coinbag.adapters.WalletAdapter
import uk.co.richyhbm.coinbag.database.entities.Wallet
import uk.co.richyhbm.coinbag.utils.AsyncWrap


class MainActivity : AppCompatActivity() {
    val viewModel = ActivityMainViewModel()
    var recyclerView: RecyclerView? = null
    var refreshLayout: SwipeRefreshLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setVariable(BR.vm, viewModel)
        binding.executePendingBindings()

        viewModel.fabIcon.set( Icons.getIcon(this, CommunityMaterial.Icon.cmd_plus, R.color.grey_50, 18))

        refreshLayout = findViewById(R.id.main_swipe_refresh_layout) as SwipeRefreshLayout
        refreshLayout!!.setOnRefreshListener {
            updateAdapter()
        }

        recyclerView = findViewById(R.id.main_recycler_view) as RecyclerView
        recyclerView!!.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView!!.layoutManager = layoutManager

        updateAdapter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onResume() {
        super.onResume()
        updateAdapter()
    }

    fun onFabClick(v: View) {
        val i = Intent(this@MainActivity, AddAccountActivity::class.java)
        startActivity(i)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh_menu -> {
                updateAdapter()
                return true
            }
            R.id.about_menu -> {
                val i = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(i)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun updateAdapter() {
        val asyncTask = object : AsyncTask<Void, Void, Array<Wallet>>() {
            override fun doInBackground(vararg params: Void): Array<Wallet> {
                val walletDao =  MyApp.database!!.walletDao()
                return walletDao.getAll().toTypedArray()
            }

            override fun onPostExecute(result: Array<Wallet>) {
                recyclerView!!.adapter = WalletAdapter(result, applicationContext)
                refreshLayout!!.isRefreshing = false
            }
        }

        asyncTask.execute()
    }
}
