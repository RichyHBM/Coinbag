package uk.co.richyhbm.coinbag.activities


import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import com.google.zxing.integration.android.IntentIntegrator
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.enums.Cryptocoins
import android.widget.AdapterView
import com.mikepenz.cryptocurrency_icons_typeface_library.CryptocurrencyIcons
import android.widget.LinearLayout
import uk.co.richyhbm.coinbag.utils.Icons


class AddAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)

        val address = findViewById(R.id.add_acc_address_entry_area) as EditText
        address.text.clear()

        val spinnerArray = ArrayList<String>()
        for(crypto in Cryptocoins.values()) {
            if(crypto.supported) {
                spinnerArray.add(crypto.getFriendlyName())
            }
        }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerArray)

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = findViewById(R.id.add_acc_type_spinner) as Spinner
        spinner.adapter = adapter

        val imageView = findViewById(R.id.add_acc_type_image) as ImageView

        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                val margin = (16 * view.context.getResources().getDisplayMetrics().density).toInt()
                lp.setMargins(margin, margin, margin, margin)
                imageView.layoutParams = lp
                when(position) {
                    0 -> imageView.setImageDrawable(Icons.getIcon(view.context, CryptocurrencyIcons.Icon.cci_btc, R.color.grey_700, 72))
                    1 -> imageView.setImageDrawable(Icons.getIcon(view.context, CryptocurrencyIcons.Icon.cci_eth, R.color.grey_700, 72))
                    else -> {}
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                val lp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                lp.setMargins(0, 0, 0, 0)
                imageView.layoutParams = lp
                imageView.setImageResource(0)
            }
        }

        val fab = findViewById(R.id.scan_fab) as FloatingActionButton

        fab.setImageDrawable( Icons.getIcon(this, CommunityMaterial.Icon.cmd_qrcode_scan, R.color.grey_50, 18))

        fab.setOnClickListener( { _: View ->
            val integrator = IntentIntegrator(this)
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES)
            integrator.setPrompt("Scan your public key!")
            integrator.setOrientationLocked(true)
            integrator.setCameraId(Camera.CameraInfo.CAMERA_FACING_BACK)
            integrator.setBeepEnabled(false)
            integrator.setBarcodeImageEnabled(true)
            integrator.initiateScan()
        })

        val cancelBtn = findViewById(R.id.add_acc_cancel) as Button
        cancelBtn.setOnClickListener { _:View -> finish() }

        val saveBtn = findViewById(R.id.add_acc_save) as Button
        saveBtn.setOnClickListener { v:View ->
            Snackbar.make(v, "Save button pressed!", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                val address = findViewById(R.id.add_acc_address_entry_area) as EditText
                address.text.clear()
                address.setText(result.contents.toCharArray(), 0, result.contents.length)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
