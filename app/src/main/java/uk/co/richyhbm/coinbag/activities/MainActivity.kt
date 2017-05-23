package uk.co.richyhbm.coinbag.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import uk.co.richyhbm.coinbag.R
import com.mikepenz.iconics.context.IconicsContextWrapper
import uk.co.richyhbm.coinbag.utils.Icons

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById(R.id.main_fab) as FloatingActionButton

        fab.setImageDrawable( Icons.getIcon(this, CommunityMaterial.Icon.cmd_plus, R.color.grey_50, 18))

        fab.setOnClickListener( { _: View ->
            val i = Intent(this@MainActivity, AddAccountActivity::class.java)
            startActivity(i)
        })

        val refreshLayout = findViewById(R.id.main_swipe_refresh_layout) as SwipeRefreshLayout
        refreshLayout.setOnRefreshListener {
            updateAdapter()
            refreshLayout.isRefreshing = false
        }

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

    }
}
